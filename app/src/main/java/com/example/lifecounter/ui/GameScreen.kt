package com.example.lifecounter.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.lifecounter.R
import com.example.lifecounter.data.Player
import com.example.lifecounter.ui.theme.Shapes

class GameScreen {

    @SuppressLint("NotConstructor")
    @Composable
    fun GameScreen(players: List<Player>,
             onSettingsClicked: () -> Unit,
             onLifeChange: (Player, Int) -> Unit,
            modifier: Modifier = Modifier){

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.padding(8.dp))
            Row (modifier = Modifier.weight(1.0F)){
                Spacer(Modifier.padding(8.dp))
                for(i in 0 until (players.size - players.size/2)){
                    Card(modifier = Modifier
                        .fillMaxHeight()
                        .weight(100.0f)
                        .graphicsLayer { rotationZ = 180f }
                        .padding(8.dp),
                        border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.secondary),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)){
                        counterCard(true, players[i+players.size/2], onLifeChange)
                    }
                }
                Spacer(Modifier.padding(8.dp))
            }

            Row(modifier = Modifier.weight(1.0F)) {
                Spacer(Modifier.padding(8.dp))
                for(i in 0 until players.size/2){
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1.0f)
                            .padding(8.dp),
                        border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.secondary),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)
                    ) {
                        counterCard(false, players[i], onLifeChange)
                    }
                }
                Spacer(Modifier.padding(8.dp))
            }
            Spacer(Modifier.padding(8.dp))

        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun counterCard(flipped: Boolean, player: Player, onLifeChange: (Player, Int) -> Unit){
        var life by remember { mutableIntStateOf(player.life) }
        var poison by remember { mutableIntStateOf(player.poisonCounters) }
        var energy by remember { mutableIntStateOf(player.energyCounters) }
        var nameDialog by remember { mutableStateOf(false) }
        var poisonDialog by remember { mutableStateOf(false) }
        var energyDialog by remember { mutableStateOf(false) }
        var name by remember { mutableStateOf(player.name) }

        Row(modifier = Modifier.fillMaxSize()){
            //contains minus button and energy/poison counters
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.weight(1.0F)){
                Row(modifier = Modifier.weight(1.0f)) {
                    Column {
                        Row(modifier = Modifier.padding(4.dp)){
                            Image(
                                painter = painterResource(R.drawable.baseline_flash_on_24),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Energy: $energy",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.clickable(
                                onClick = {energyDialog = true}
                                )
                            )
                        }

                        if(energyDialog){
                            poisonEnergyDialog(
                                dismiss = {energyDialog = false},
                                title = "Energy",
                                value = energy,
                                add = {energy += 1
                                        player.energyCounters = energy},
                                minus = {energy -= 1
                                    player.energyCounters = energy})
                        }

                        Row(modifier = Modifier.padding(4.dp)) {
                            Image(
                                painter = painterResource(R.drawable.poisoncounter),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                "Poison: $poison",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.clickable(
                                    onClick = { poisonDialog = true }
                                )
                            )
                        }
                        if(poisonDialog){
                            poisonEnergyDialog(
                                dismiss = {poisonDialog = false},
                                title = "Poison",
                                value = poison,
                                add = {poison += 1
                                    player.poisonCounters = poison},
                                minus = {poison -= 1
                                    player.poisonCounters = poison})
                        }
                    }
                }

                Row(Modifier.weight(1.0f)) {
                    Button(
                        onClick = {
                        },
                        shape = CircleShape,
                        modifier = Modifier.size(30.dp),
                        colors = ButtonDefaults.buttonColors()
                            .copy(containerColor = MaterialTheme.colorScheme.scrim),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "-",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(0.dp)
                                .combinedClickable(
                                    onClick = {
                                        life -= 1
                                        player.life = life
                                    },
                                    onLongClick = {
                                        life -= 30
                                        player.life = life
                                    }
                                )
                        )
                    }
                }
            }

            //contains name button/label and life label
            Column(modifier = Modifier.weight(2.0F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = player.name,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(
                            onClick = { nameDialog = true }
                        )
                )
                if(nameDialog){
                    Dialog(onDismissRequest = {nameDialog = false},
                        content = {
                            Card(modifier = Modifier
                                .height(200.dp)
                                .width(200.dp),border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.secondary),
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)) {
                                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Enter New Name Here:",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    OutlinedTextField(
                                        value = name,
                                        onValueChange = {
                                            name = it
                                            player.name = name
                                        },
                                        modifier = Modifier,
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Done
                                        )
                                    )
                                    TextButton(
                                        onClick = { nameDialog = false },
                                        colors = ButtonDefaults.buttonColors()
                                            .copy(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                                    ) {
                                        Text(
                                            text = "Submit Name",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.scrim
                                        )
                                    }
                                }
                            }
                        })
                }

                Spacer(Modifier.padding(18.dp))

                Text(
                    text = life.toString(),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center
                )
            }

            //contains plus button and commander damage button
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.weight(1.0F)){
                Row (modifier = Modifier.weight(1.0f)){
                    Button(onClick = { player.commanderDamage }) {
                        Text(
                            text = "Hi",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                Row(modifier = Modifier.weight(1.0f)) {
                    Button(
                        onClick = {
                        },
                        shape = CircleShape,
                        modifier = Modifier.size(30.dp),
                        colors = ButtonDefaults.buttonColors()
                            .copy(containerColor = MaterialTheme.colorScheme.scrim),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "+",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(0.dp)
                                .combinedClickable(
                                    onClick = {
                                        life += 1
                                        player.life = life
                                    },
                                    onLongClick = {
                                        life += 30
                                        player.life = life
                                    }
                                )
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun poisonEnergyDialog(dismiss:()->Unit, title:String, value: Int, add:()->Unit, minus:()-> Unit){
        Dialog(
            onDismissRequest = dismiss,
            content = {
                Card(modifier = Modifier
                    .height(200.dp)
                    .width(300.dp)
                    ,border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.secondary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)
                ) {
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Change $title",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .weight(1.0f, false)
                                .padding(8.dp)
                        )
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .weight(2.5f), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1.0f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Button(
                                    onClick = minus,
                                    shape = CircleShape,
                                    modifier = Modifier.size(30.dp),
                                    colors = ButtonDefaults.buttonColors()
                                        .copy(containerColor = MaterialTheme.colorScheme.scrim),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text(
                                        text = "-",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .padding(0.dp)
                                    )
                                }
                            }

                            Column(modifier = Modifier.weight(2.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = value.toString(),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    style = MaterialTheme.typography.displayMedium
                                )
                            }

                            Column(modifier = Modifier.weight(1.0f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Button(
                                    onClick = add,
                                    shape = CircleShape,
                                    modifier = Modifier.size(30.dp),
                                    colors = ButtonDefaults.buttonColors()
                                        .copy(containerColor = MaterialTheme.colorScheme.scrim),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text(
                                        text = "+",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .padding(0.dp)
                                    )
                                }
                            }

                        }
                        Row(modifier = Modifier
                            .weight(1.5f)
                            .padding(4.dp)){
                            Button(onClick = {dismiss()},
                                colors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                            ) {
                                Text(
                                    text = "Done",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.scrim
                                )
                            }
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun commanderDialog(dismiss:()->Unit, player:Player){
        Dialog(
            onDismissRequest = dismiss,
            content = {
                Card(modifier = Modifier
                    .height(200.dp)
                    .width(300.dp)
                    ,border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.secondary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)
                ){
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Commander Damage",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1.0f, false)
                        )

                        for (i in 0 until player.commanderDamage.size) {
                            var damage by remember { mutableIntStateOf(player.commanderDamage[i]) }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.0f)) {
                                Column(modifier = Modifier.weight(1.0f), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Button(onClick = {
                                        damage -= 1
                                        player.commanderDamage[i] = damage
                                    }) {

                                    }
                                }
                                Column(modifier = Modifier.weight(2.0f), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = damage.toString(),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Column(modifier = Modifier.weight(1.0f), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Button(onClick = {
                                        damage += 1
                                        player.commanderDamage[i] = damage
                                    }) {

                                    }
                                }
                            }
                        }

                        Button(onClick = {dismiss()}, modifier = Modifier.weight(1.0f, false),
                            colors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                            Text(
                                text = "Done",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.scrim
                            )
                        }
                    }
                }
            }
        )
    }
}
