package com.example.lifecounter.ui

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.lifecounter.R
import com.example.lifecounter.ui.GameViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

/** This class holds all the composable methods and data for the construction of the start screen of the app**/
class StartScreen {


    @Composable
    fun TestScreen() {
        var lifeInput by remember { mutableStateOf("") }
        var lifeTotal by remember { mutableStateOf(0) }

        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = lifeInput,
                onValueChange = {
                    lifeInput = it
                    lifeTotal = it.toIntOrNull() ?: 0
                },
                label = { Text("Enter Starting Life") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Parsed life total: $lifeTotal")
        }
    }

    /** Public method to call to construct the start screen
     * @param modifier regular modifier for the composition
     * @param onStartPressed button for starting the game after selecting initial configuration
     */
    @Composable
    fun startScreen(
        modifier: Modifier = Modifier,
        onStartPressed: (Int, Int) -> Unit
    ) {
        /**Amount of players to play the game with**/
        var playerAmount: Int by remember { mutableStateOf(0) }

        /**Life to start the game with**/
        var lifeTotal: Int by remember { mutableStateOf(0)}

        var lifeInput: String by remember { mutableStateOf("") }

        Column(modifier = modifier){
            titleCard("MTG LifeCounter")

            configurations(playerAmount,lifeTotal, lifeInput, {playerAmount = it},
                {lifeInput = it
                lifeTotal = it.toIntOrNull() ?: 0 })
            startButton(
                onStartPressed = onStartPressed,
                playerAmount = playerAmount,
                lifeTotal = lifeTotal
            )
        }
    }

    /** Displays the name of the app
     * @param title name of the app
     * @param modifier regular modifier for the composition
     */
    @Composable
    private fun titleCard(title: String, modifier: Modifier = Modifier){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth()) {
            Text(
                text = title,
                modifier = modifier.offset(y = 20.dp).padding(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
    /** Constructs the configurations to control the amount of players in the game
     * and the starting life of each player
     * @param modifier regular modifier for the composition
     */
    @Composable
    private fun configurations(playerAmount: Int, lifeTotal:Int, lifeInput:String, onSelectClicked: (Int) -> Unit,
                               onValueChange: (String) -> Unit, modifier: Modifier = Modifier){
        var color = MaterialTheme.colorScheme.primaryContainer
        var darkColor = MaterialTheme.colorScheme.inversePrimary
        val click = onSelectClicked
        Row (horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min).width(intrinsicSize = IntrinsicSize.Min)
            .padding(8.dp)){
            Column(horizontalAlignment = Alignment.End, modifier = modifier.padding(8.dp).
            width((LocalConfiguration.current.screenWidthDp/4).dp) ){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Number of Players",
                        modifier = modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {

                        Button(
                            onClick = { click(2) },
                            modifier = modifier.padding(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (playerAmount == 2) darkColor else color
                            )
                        ) {
                            Text(
                                text = "2",
                                modifier = modifier,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        Button(
                            onClick = { click(3) },
                            modifier = modifier.padding(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (playerAmount == 3) darkColor else color
                            )
                        ) {
                            Text(
                                text = "3",
                                modifier = modifier,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                    Row {

                        Button(
                            onClick = { click(4) },
                            modifier = modifier.padding(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (playerAmount == 4) darkColor else color
                            )
                        ) {
                            Text(
                                text = "4",
                                modifier = modifier,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }


                        Button(
                            onClick = { click(5) },
                            modifier = modifier.padding(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (playerAmount == 5) darkColor else color
                            )
                        ) {
                            Text(
                                text = "5",
                                modifier = modifier,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                    }

                    Button(
                        onClick = { click(6) },
                        modifier = modifier.padding(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (playerAmount == 6) darkColor else color
                        )
                    ) {
                        Text(
                            text = "6",
                            modifier = modifier,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxHeight()) {
                Image(
                    painter = painterResource(R.drawable.white_straight_line),
                    contentDescription = "line",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(10.dp, 200.dp)
                )
            }

            Column(verticalArrangement = Arrangement.Center, modifier = modifier.fillMaxHeight().width(
                (LocalConfiguration.current.screenWidthDp/4).dp)) {
                TextField(
                    value = lifeInput,
                    onValueChange = onValueChange,
                    label = { Text("Enter Starting Life", style = MaterialTheme.typography.labelSmall)},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    textStyle = MaterialTheme.typography.labelSmall,
                    modifier = modifier.padding(8.dp).size(170.dp, 50.dp)
                )
            }
        }
    }

    /** Constructs the button to begin the game
     * @param onStartPressed takes in two integers and uses them to initialize the uiState and start the game
     * @param modifier regular modifier for the composition
     */
    @Composable
    private fun startButton(onStartPressed: (Int, Int) -> Unit, playerAmount: Int, lifeTotal: Int,
                    modifier: Modifier = Modifier){

        Column(verticalArrangement = Arrangement.Bottom, modifier = modifier.fillMaxWidth().fillMaxHeight()) {
            Button(
                enabled = playerAmount != 0 && lifeTotal != 0,
                onClick = {onStartPressed(playerAmount, lifeTotal)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)

            ) {
                Text(
                    text = "Start",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    }

}