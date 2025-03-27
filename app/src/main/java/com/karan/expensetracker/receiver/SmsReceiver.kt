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

                if(canBeTransaction(message.message)) {
                    val workRequest = OneTimeWorkRequestBuilder<ApiWorker>()
                        .setInputData(workDataOf("data" to message.message))
                        .build()

                    workManager.enqueue(workRequest)
                }

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


    private fun canBeTransaction(message: String): Boolean {
        val normalizedMessage = message
            .replace(Regex("""(\d),(\d)"""), "$1$2")
            .lowercase()

        // Negative patterns (reject immediately)
        val negativePatterns = setOf(
            "password", "login", "offer", "discount", "advertisement",
            "promotion", "coupon", "winner", "free", "warning", "otp"
        ).joinToString("|")

        if (Regex("""\b($negativePatterns)\b""").containsMatchIn(normalizedMessage)) {
            return false
        }

        // Transaction success/failure detection
        val successKeywords = setOf(
            "credited", "debited", "paid", "received", "withdrawn",
            "sent", "completed", "settled", "processed", "successfully",
            "ref no", "txn id", "upi", "imps", "neft", "rtgs"
        )

        val failureKeywords = setOf(
            "declined", "failed", "reversed", "unsuccessful",
            "error", "rejected", "cancelled"
        )

        // Currency and bank patterns (unchanged)
        val patterns = listOf(
            Regex("""\b(${successKeywords.joinToString("|")})\b"""),
            Regex("""(rs\.?|inr|₹)\s*(\d+\.?\d*)|(\d+\.?\d*)\s*(rs\.?|inr|₹)"""),
            Regex("""(ref\s*(no|id)|txn|transaction|upi)[:\s-]+(\w{8,20})"""),
            Regex("""\b(sbi|hdfc|icici|axis|kotak|pnb|bob|yes\s?bank)\b""")
        )

        // Check for transaction failure first
        if (Regex("""\b(${failureKeywords.joinToString("|")})\b""").containsMatchIn(normalizedMessage)) {
            return false
        }

        // Calculate success score
        val score = patterns.count { it.containsMatchIn(normalizedMessage) }
        val threshold = if (normalizedMessage.length < 50) 3 else 2

        return score >= threshold
    }

}

val transactionSMSEdgeCases = arrayOf(
    // Valid Transactions (should return true)
    "UPI: ₹1.50 paid to chaiwala@okaxis. Bal: ₹123.45 - SBI",
    "IMPS: INR1,23,456 sent to XX7890 (Ref: 789GHI456)",
    "A/C XX1234 debited ₹999.00 for FASTAG recharge. Bal: ₹5,678 - ICICI",
    "NEFT Credit: ₹50,000.00 from JOHN DOE (SBI A/C)",
    "Wallet: ₹200 cashback credited (Txn ID: PAYTM2023)",
    "AXISBank: ₹5,000 debited for VIDYAEDU.IN (UPI Ref 789)",
    "Kotak: A/C XX5678 credited ₹12,345 via NEFT-ICICI",
    "SBI YONO: ₹2,300 spent on CREDIT CARD XX7890",
    "PhonePe: ₹500 cashback expired (Txn ID: PHONEPE2023)",
    "HDFC Loan: ₹25,678 EMI debited (A/C XX1234)",

    // Failed/Declined Transactions (should return false)
    "Transaction declined: Insufficient funds for ₹5,000 payment",
    "UPI payment of ₹1,234 failed due to network error",
    "Your ₹10,000 loan EMI was reversed (TXN ID: LOAN789)",
    "Payment of ₹2,345.00 to AMAZON.IN declined due to invalid OTP. Retry within 10 mins -Axis Bank",

    // Ambiguous Amounts
    "Your balance is ₹0.00 (Account: XX5678)",
    "₹1 credited as cashback (Paytm Wallet)",
    "Interest earned: ₹12,345.67 (FD A/C XX9012)",

    // Non-Transaction Messages (should return false)
    "Your OTP for login is 7890 - HDFC Bank",
    "Get 50% off on Amazon Pay with ICICI CC",
    "Account statement generated for XX1234 (SBI)",
    "Warning: Your account will be locked in 24 hrs",
    "UPI handle updated successfully for XX1234",

    // Currency Format Variations
    "INR 1.5L credited to A/C XX5678 (IMPS)",
    "Rs 12345.67 withdrawn from ATM (TXN: 456XXXX)",
    "₹1,23,456.78 maturity amount for FD XX7890",

    // Partial Matches
    "Your account has been debited by fraudsters - call 1800-1234",
    "Balance transfer offer: 0% interest for 6 months",

    // International Transactions
    "$500.00 charged on ICICI Travel Card XX1234 (FEE: ₹37)",
    "Forex Card: €1,000 loaded (INR 89,456.78 debited)",

    // Cryptic Bank Formats
    "ICICI: Dr ₹1,234.56@AMAZON.IN. Avl Bal ₹5,678.90",
    "SBI: Cr ₹50,000 (NEFT) to A/C XX1234. Ref: NEFT123",

    // Language Mixes (Hindi-English)
    "UPI किया: ₹500 paid to किराना स्टोर (Ref: 789ABC)",
    "A/C XX1234 ने क्रेडिट किया ₹1,000 (SBI)",

    // Special Characters
    "Payment of ₹1,000* to SWIGGY (TXN ID: SWG#2023)",
    "UPI: ₹500.00 paid to 'friend@upi' (Ref: TXN-123)",

    // Date/Time Formats
    "A/C XX1234 debited ₹2,000 on 31/12/23 18:30 IST",
    "Credit of ₹5,000 at 2023-12-31T18:30:00Z (NEFT)",

    // Edge Cases from Original Examples
    "Alert: INR 2,500.00 debited from A/C XX9012 on 20-07-2023 to VPA mobile@upi. Bal: INR 32,100.00 -ICICI",
    "Alert: ₹10,000.00 POS transaction at RELIANCE SMART using Axis DC XX1234. Avl Bal: ₹32,109.00"
)
