package com.example.fitnessclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.snap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.data.Membership
import com.example.fitnessclub.ui.theme.FitnessClubTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val fs = Firebase.firestore
    val list = remember {
        mutableStateOf(emptyList<Membership>())
    }
    fs.collection("memberships").addSnapshotListener{snapShot,exception ->
        list.value = snapShot?.toObjects(Membership::class.java) ?: emptyList()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth(0.8f)
        ) {
            items(list.value){ membership ->
                Card(modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)
                ) {
                    Text(text = membership.name,
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentWidth()
                            .padding(15.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
            onClick = {
            fs.collection("memberships")
                .document().set(
                    Membership(
                        name = "A1",
                        price = "54",
                        description = "Карабалбылды",
                        category = "categori",
                        imageUrl = "Url"
                    )
                )
        }) {

        }

    }
    Text(
        text = "Add membership"
    )
}
