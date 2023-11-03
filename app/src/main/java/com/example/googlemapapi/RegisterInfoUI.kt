
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.googlemapapi.BottomBar
import com.example.googlemapapi.DataBase
import com.example.googlemapapi.TopAppBarSample
import com.example.googlemapapi.type.PassengerInput
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterInfoUI(modifier: Modifier = Modifier, isButtonClicked: Boolean ) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var departure by remember { mutableStateOf(TextFieldValue()) }
    var destination by remember { mutableStateOf(TextFieldValue()) }
    var departureTime by remember { mutableStateOf(TextFieldValue()) }
    var capacity by remember { mutableStateOf(TextFieldValue()) }
    Box(){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-35).dp)
        ) {
            val width = size.width
            val height = size.height
            drawRect(
                color = Color(197,198,184),
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
                .padding(35.dp)
                .offset(y = (-40).dp),
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
        }
    }
    LaunchedEffect(isButtonClicked) {
        if (isButtonClicked) {
            val dataBase = DataBase()

            val nameValue = name.text
            val departureValue = departure.text
            val destinationValue = destination.text
            val departureTimeValue = departureTime.text
            val capacityValue = capacity.text

            try {
                dataBase.executeMutation(
                    uuid = UUID.randomUUID().toString(),
                    name = nameValue,
                    departure = departureValue,
                    destination = destinationValue,
                    time = departureTimeValue,
                    capacity = capacityValue.toInt(),
                    passenger = 0,
                    passengers = listOf(PassengerInput("hamada", "こんにちわ"))
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

@Composable
fun RegisterScreen(navController: NavController){
    Column {
        var isButtonClicked by remember { mutableStateOf(false) }
        TopAppBarSample()
        BottomBar(navController, isButtonClicked = isButtonClicked, setButtonClicked = { isButtonClicked = it }) { innerPadding ->
            RegisterInfoUI(modifier = Modifier.padding(innerPadding), isButtonClicked = isButtonClicked)
        }
    }
}
