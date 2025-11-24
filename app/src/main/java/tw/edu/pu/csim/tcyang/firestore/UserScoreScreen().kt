package tw.edu.pu.csim.tcyang.firestore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserScoreScreen( userScoreViewModel: UserScoreViewModel = viewModel()
) {
    var score by remember { mutableStateOf("") }
    OutlinedTextField(
        value = score,
        onValueChange = { score = it },
        label = { Text("分數") },
        placeholder = { Text("請輸入您的分數") },
        keyboardOptions = KeyboardOptions
            (keyboardType = KeyboardType.Number)
    )
    Text("您的分數是：$score")
    Spacer(modifier = Modifier.size(10.dp))


    Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("彣媞", 39)
            userScoreViewModel.addUser(userScore)
        }) {
            Text("新增資料")
        }

        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("彣媞", 21)
            userScoreViewModel.updateUser(userScore)
        }) {
            Text("新增/異動資料")
        }
        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("彣媞", 21)
            userScoreViewModel.deleteUser(userScore)
        }) {
            Text("刪除資料")
        }
        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式
            var userScore = UserScoreModel("彣媞", 21)
            userScoreViewModel.getUser(userScore)
        }) {
            Text("查詢資料")
        }
        Button(onClick = {
            // 在按鈕點擊時，直接呼叫 ViewModel 的函式

            userScoreViewModel.orderUser()
        }) {
            Text("查詢前三名")
        }

        Text(userScoreViewModel.message)
    }

