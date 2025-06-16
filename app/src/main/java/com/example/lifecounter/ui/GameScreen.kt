package com.example.lifecounter.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.window.DialogProperties
import com.example.lifecounter.R
import com.example.lifecounter.data.Counter
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
                        border = BorderStroke(Dp.Hairline*3, MaterialTheme.colorScheme.secondary),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)){
                        counterCard(true, players[i+players.size/2], players, onLifeChange)
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
                        border = BorderStroke(Dp.Hairline*3, MaterialTheme.colorScheme.secondary),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)
                    ) {
                        counterCard(false, players[i], players, onLifeChange)
                    }
                }
                Spacer(Modifier.padding(8.dp))
            }
            Spacer(Modifier.padding(8.dp))

        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun counterCard(flipped: Boolean, player: Player, players: List<Player>, onLifeChange: (Player, Int) -> Unit){
        var life by remember { mutableIntStateOf(player.life) }
        var poison by remember { mutableIntStateOf(player.poisonCounters) }
        var energy by remember { mutableIntStateOf(player.energyCounters) }
        var nameDialog by remember { mutableStateOf(false) }
        var counterDialog by remember { mutableStateOf(false) }
        var commanderDialog by remember { mutableStateOf(false) }
        var name by remember { mutableStateOf(player.name) }

        Row(modifier = Modifier.fillMaxSize()){
            //contains minus button and energy/poison counters
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.weight(1.0F)){
                Row(modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(R.drawable.baseline_menu_24),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(0.dp, 1.dp)
                                .clickable(
                                    onClick = { counterDialog = true }
                                ))
                        if(counterDialog){
                            counterMenu(flipped, {counterDialog = false}, player)
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
                    Dialog(onDismissRequest = {nameDialog = false
                                              name= name.trim()
                                              player.name = name},
                        content = {
                            Card(modifier = Modifier
                                .height(200.dp)
                                .width(350.dp)
                                .graphicsLayer {
                                    rotationZ = if (flipped) 180f else 0f
                                }
                                ,border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.secondary),
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)) {
                                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Column(modifier = Modifier.weight(1.0f)) {
                                        Text(
                                            text = "Enter New Name:",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.primary

                                        )
                                    }
                                    Column(modifier = Modifier.weight(1.0f)) {
                                        OutlinedTextField(
                                            value = name,
                                            onValueChange = {
                                                name = it
                                                player.name = name
                                            },
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                                keyboardType = KeyboardType.Text,
                                                imeAction = ImeAction.Done
                                            )
                                        )
                                    }
                                    Column(modifier = Modifier.weight(1.0f)) {
                                        TextButton(
                                            onClick = {
                                                nameDialog = false
                                                name = name.trim()
                                                player.name = name
                                            },
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
                Row(modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth()) {

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(R.drawable.commander),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(
                                    onClick = { commanderDialog = true }
                                ))
                    }
                }
                if(commanderDialog){
                    commanderDialog(flipped, {commanderDialog = false}, player,players)
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
    fun addCounter(flipped:Boolean, dismiss: () -> Unit, player: Player){
        var name by remember { mutableStateOf("") }
        Dialog(onDismissRequest = { dismiss()
            name = name.trim()
            player.countersList.add(Counter(name, 0))},
            content = {
                Card(modifier = Modifier
                    .height(200.dp)
                    .width(350.dp)
                    .graphicsLayer {
                        rotationZ = if (flipped) 180f else 0f
                    }
                    ,border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.secondary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)) {
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Column(modifier = Modifier.weight(1.0f)) {
                            Text(
                                text = "Enter Counter Name:",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Column(modifier = Modifier.weight(1.0f)) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = {
                                    name = it
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                )
                            )
                        }
                        Column(modifier = Modifier.weight(1.0f)) {
                            TextButton(
                                onClick = {
                                    dismiss()
                                    name = name.trim()
                                    player.countersList.add(Counter(name, 0))
                                },
                                colors = ButtonDefaults.buttonColors()
                                    .copy(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                            ) {
                                Text(
                                    text = "Submit Counter",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.scrim
                                )
                            }
                        }
                    }
                }
            })
    }

    @Composable
    fun counterMenu(flipped:Boolean, dismiss: () -> Unit, player: Player){
        val scrollstate = rememberScrollState()
        var addCounterDialog by remember { mutableStateOf(false) }
        Dialog(dismiss) {
            Card(
                modifier = Modifier
                    .height(400.dp)
                    .width(250.dp)
                    .graphicsLayer {
                        rotationZ = if (flipped) 180f else 0f
                    },
                border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.secondary),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .height(32.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1.0f)
                                .padding(2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Counters",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1.0f)
                                .padding(2.dp, 6.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Image(
                                painter = painterResource(R.drawable.baseline_add_circle_24),
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable(
                                        onClick = { addCounterDialog = true }
                                    ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                            if(addCounterDialog){
                                addCounter(flipped, {addCounterDialog = false}, player)
                            }
                        }
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollstate)) {
                        for (i in 0 until player.countersList.size) {
                            var dialog by remember { mutableStateOf(false) }
                            var value by remember { mutableIntStateOf(player.countersList[i].value) }
                            Text(
                                text = player.countersList[i].name + ": " + player.countersList[i].value,
                                modifier = Modifier
                                    .clickable(
                                        onClick = { dialog = true }
                                    )
                                    .padding(10.dp, 3.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            if (dialog) {
                                counterDialog(flipped, { dialog = false },
                                    player.countersList[i].name,
                                    value,
                                    {
                                        value += 1
                                        player.countersList[i].value = value
                                    },
                                    {
                                        value -= 1
                                        player.countersList[i].value = value
                                    },
                                    {player.countersList.removeAt(i)})
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun counterDialog(flipped:Boolean, dismiss:()->Unit, title:String, value: Int, add:()->Unit, minus:()-> Unit, remove:()-> Unit){
        Dialog(
            onDismissRequest = dismiss,
            content = {
                Card(modifier = Modifier
                    .height(220.dp)
                    .width(300.dp)
                    .graphicsLayer {
                        rotationZ = if (flipped) 180f else 0f
                    }
                    ,border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.secondary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)
                ) {
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$title Counter",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .weight(1.2f, false)
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
                            .weight(2.0f)
                            .padding(4.dp)){
                            Button(onClick = {remove()
                                             dismiss()},
                                colors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                            ) {
                                Text(
                                    text = "Remove Counter",
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
    fun commanderDialog(flipped:Boolean, dismiss:()->Unit, player:Player, players: List<Player>){
        Dialog(
            onDismissRequest = dismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnClickOutside = true,
                dismissOnBackPress = true, decorFitsSystemWindows = false),
            content = {
                Box(modifier = Modifier
                    .wrapContentWidth().wrapContentHeight()
                    .padding(0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .height(350.dp)
                            .width(500.dp)
                            .graphicsLayer {
                                rotationZ = if (flipped) 180f else 0f

                            },
                        border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.secondary),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.scrim)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Commander Damage",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .weight(2.25f, false)
                                    .padding(6.dp)
                            )
                            if (!flipped) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(2.5f)
                                ) {
                                    CommanderCard(
                                        player,
                                        players,
                                        player.commanderDamage.size / 2,
                                        player.commanderDamage.size,
                                        flipped,
                                        false,
                                        Modifier
                                            .wrapContentHeight()
                                            .weight(1.0f)
                                    )
                                }
                                Spacer(Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(2.5f)
                                )
                                {
                                    CommanderCard(
                                        player,
                                        players,
                                        0, player.commanderDamage.size / 2,
                                        !flipped,
                                        false,
                                        Modifier
                                            .wrapContentHeight()
                                            .weight(1.0f)
                                    )
                                }
                            } else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(2.5f)
                                ) {
                                    CommanderCard(
                                        player,
                                        players,
                                        0, player.commanderDamage.size / 2,
                                        !flipped,
                                        true,
                                        Modifier
                                            .wrapContentHeight()
                                            .weight(1.0f)
                                    )
                                }

                                Spacer(Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(2.5f)
                                )
                                {
                                    CommanderCard(
                                        player,
                                        players,
                                        player.commanderDamage.size / 2,
                                        player.commanderDamage.size,
                                        flipped,
                                        true,
                                        Modifier
                                            .wrapContentHeight()
                                            .weight(1.0f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun CommanderCard(player: Player, players: List<Player>, start: Int, end:Int, flipped: Boolean, overallFlip:Boolean, modifier: Modifier){
        var offset = if(overallFlip) 2 else 0
        for (j in start until end) {
            var i = j + offset
            var damage by remember { mutableIntStateOf(player.commanderDamage[i]) }
            Card(modifier = modifier
                .graphicsLayer {
                    rotationZ = if(flipped) 0f else 180f
                },
                colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.scrim),
                border = BorderStroke(1.dp,MaterialTheme.colorScheme.primary)
            ) {
                Column(Modifier.fillMaxWidth().weight(1.0f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = players[i].name,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .padding(10.dp, 2.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.0f)
                ) {
                    Column(
                        modifier = Modifier.weight(1.0f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                damage -= 1
                                player.commanderDamage[i] = damage
                            },
                            shape = CircleShape,
                            modifier = Modifier.size(25.dp),
                            colors = ButtonDefaults.buttonColors()
                                .copy(containerColor = MaterialTheme.colorScheme.scrim),
                            border = BorderStroke(
                                1.dp, MaterialTheme.colorScheme.primaryContainer
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
                    Column(
                        modifier = Modifier.weight(2.0f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = damage.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1.0f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                damage += 1
                                player.commanderDamage[i] = damage
                            },
                            shape = CircleShape,
                            modifier = Modifier.size(25.dp),
                            colors = ButtonDefaults.buttonColors()
                                .copy(containerColor = MaterialTheme.colorScheme.scrim),
                            border = BorderStroke(
                                1.dp, MaterialTheme.colorScheme.primaryContainer
                            ),
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
            }
            if(overallFlip){
                offset -= 2
            }
        }
    }
}
