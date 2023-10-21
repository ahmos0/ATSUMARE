
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RegisterInfoUI() {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var departure by remember { mutableStateOf(TextFieldValue()) }
    var destination by remember { mutableStateOf(TextFieldValue()) }
    var departureTime by remember { mutableStateOf(TextFieldValue()) }
    var capacity by remember { mutableStateOf(TextFieldValue()) }
    var isButtonClicked by remember { mutableStateOf(false) }
    Box(){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            drawRect(
                color = Color(197,198,184),  // 背景色を指定
                size = Size(width, height)
            )
            drawRoundRect(
                color = Color(217, 217, 217),
                topLeft = Offset(60f, 120f),
                size = Size(width - 2 * 60f, height - 2 * 120f),
                cornerRadius = CornerRadius(x = 100F, y = 100F)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(35.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "名前",
                color = Color.Black
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("名前") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "出発地",
                color = Color.Black
            )
            OutlinedTextField(
                value = departure,
                onValueChange = { departure = it },
                label = { Text("出発地") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "目的地",
                color = Color.Black
            )
            OutlinedTextField(
                value = destination,
                onValueChange = { destination = it },
                label = { Text("目的地") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "出発時刻",
                color = Color.Black
            )
            OutlinedTextField(
                value = departureTime,
                onValueChange = { departureTime = it },
                label = { Text("出発時刻") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "定員",
                color = Color.Black
            )
            OutlinedTextField(
                value = capacity,
                onValueChange = { capacity = it },
                label = { Text("定員") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    isButtonClicked = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(210,180,140),
                    contentColor = Color.White
                )
                ) {
                Text(
                    text = "登録",
                    color = Color.White
                )
            }
        }
    }
}

