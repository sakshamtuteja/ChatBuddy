package com.example.chatapp.Screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.CommonDivider
import com.example.chatapp.CommonImage
import com.example.chatapp.LCViewModel
import com.example.chatapp.commonProgressBar
import java.net.URL

@Composable

fun ProfileScreen(navController: NavController, vm: LCViewModel) {
    val inProgress = vm.inProcess.value

    if (inProgress) {
        commonProgressBar()
    } else {
        Column {
            ProfileContent(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp),
                vm=vm,
                name="",
                number="",
                onNameChange = {""},
                onNumberChange = {""},
                onSave = {},
                onBack = {},
                onLogout = {}

            )

            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.PROFILE, navcontroller = navController
            )
        }

    }


}



@Composable

fun ProfileContent(
    modifier: Modifier,
    vm: LCViewModel,
    name:String,
    number: String,
    onNameChange:(String)->Unit,
    onNumberChange:(String)->Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onLogout: ()->Unit
) {
    val imageUrl = vm.userData.value?.imageUrl
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", Modifier.clickable {
                onBack.invoke()
            })
            Text(text = "Save", Modifier.clickable {
                onSave.invoke()
            })
        }
            CommonDivider()

            ProfileImage(imageURL = imageUrl, vm = vm)
            CommonDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {Text(text = "Name",modifier=Modifier.width(100.dp))
                TextField(value =name , onValueChange =onNameChange,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,

                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,

                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Number",modifier=Modifier.width(100.dp))
                TextField(value =number , onValueChange =onNumberChange,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,

                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,

                        )
                )
            }
            CommonDivider()
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.Center){
Text(text = "LogOut", modifier = Modifier.clickable { onLogout.invoke() })

            }

    }
}

@Composable
fun ProfileImage(imageURL: String?, vm: LCViewModel) {
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                vm.uploadProfileimage(uri)
            }
        }
    Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    launcher.launch("image/*")
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                CommonImage(data = imageURL)
            }
            Text(text = "Change Profile Picture")
        }
        if (vm.inProcess.value) {
            commonProgressBar()
        }
    }
}
