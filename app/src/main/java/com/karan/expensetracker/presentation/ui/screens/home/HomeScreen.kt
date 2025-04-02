package com.karan.expensetracker.presentation.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel : HomeViewModel = hiltViewModel()


    Scaffold (
        modifier = modifier.fillMaxSize().background(White)
    ){



        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(top = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Row(
                modifier = Modifier.padding( vertical =  20.dp )
            ){
                Text("Current expenses")
                Text(" Rs 11,300")
            }

            Card (
                modifier = Modifier.fillMaxWidth(0.8F).fillMaxHeight(0.3F)
            ){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){

                    Text("Chart")
                }
            }
            Text("All Expenses       >")
            Spacer(modifier = Modifier.height(20.dp))

            Text("Recent Expenses")
            Spacer(modifier = Modifier.height(10.dp))

            Text("Amazon.in   Rs 1100    yesterday")
            Spacer(modifier = Modifier.height(5.dp))

            Text("Amazon.in   Rs 1100    yesterday")
            Spacer(modifier = Modifier.height(5.dp))

            Text("Amazon.in   Rs 1100    yesterday")
            Spacer(modifier = Modifier.height(5.dp))

            Text("Amazon.in   Rs 1100    yesterday")
            Spacer(modifier = Modifier.height(5.dp))

            Text("Amazon.in   Rs 1100    yesterday")
            Spacer(modifier = Modifier.height(5.dp))

            Text("Amazon.in   Rs 1100    yesterday")
            Spacer(modifier = Modifier.height(5.dp))



        }

    }

}


@Preview
@Composable
private fun HomePreview() {
    HomeScreen()
}