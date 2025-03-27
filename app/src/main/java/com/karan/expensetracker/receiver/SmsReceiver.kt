package com.karan.expensetracker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

import androidx.work.workDataOf
import com.karan.expensetracker.models.MultipartMessage
import com.karan.expensetracker.models.Message
import com.karan.expensetracker.worker.ApiWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SmsReceiver : BroadcastReceiver() {

    @Inject lateinit var workManager : WorkManager

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle = intent.extras
            bundle?.let {
                val pdu = it.get("pdus") as Array<*>
                val format = it.getString("format") as String
                val message : Message = convertIntoMsg(pdu , format)
                Log.e("TAG", "onReceive: $message", )

                val workRequest = OneTimeWorkRequestBuilder<ApiWorker>()
                    .setInputData(workDataOf("data" to message.message))
                    .build()

                workManager.enqueue(workRequest)

            }


        }
    }



    private fun convertIntoMsg(pdus : Array<*> , format : String) : Message{

        var timestamp : Long = 0L
        var from : String = ""


        val multipartMsg : List<MultipartMessage>?  = pdus.map { msgByte ->
            val pdus = msgByte as ByteArray
            val smsMessage = SmsMessage.createFromPdu(pdus , format)

            timestamp = smsMessage.timestampMillis
            from = smsMessage.displayOriginatingAddress

            MultipartMessage(
                from = smsMessage.displayOriginatingAddress,
                message = smsMessage.messageBody,
                data = pdus
            )
        }

        val message : StringBuilder = StringBuilder()
        multipartMsg?.forEach { msg ->
            message.append(msg.message)
        }

        return Message(
            from = from,
            message = message.toString(),
            timestamp = timestamp
        )
    }

    private fun isTransactionSMS(messageBody: String, sender: String): Boolean {
        // Check sender (optional: known bank shortcodes)
        val isBankSender = sender in listOf("ABCBK", "BANKXYZ", "PAY" , "ICICI")

        // Check for transaction keywords
        val transactionKeywords = listOf(
            "debited", "credited", "transaction", "payment",
            "balance", "card", "rs.", "inr", "₹", "merchant",
            "acct"
        )

        // Regex to detect amount patterns (e.g., ₹1,250.00)
//        val amountPattern = Regex("""(₹|\$|Rs?\.?)\s*\d{1,3}(,\d{3})*(\.\d{2})?""")
        val transactionRegex = Regex(
            "\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\b|" + // UPI
                    "\\b(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|" +
                    "3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|" +
                    "6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35\\d{3})\\d{11})\\b|" + // Card
                    "\\b(?:NEFT|RTGS)\\b|" + // NEFT
                    "\\b(?:international transaction|USD|EUR|GBP)\\b", // International
            RegexOption.IGNORE_CASE
        )

        // Check if message matches keywords or amount patterns
        return isBankSender ||
                transactionKeywords.any { keyword ->
                    messageBody.contains(keyword, ignoreCase = true)
                } ||
                transactionRegex.containsMatchIn(messageBody)
    }

}
