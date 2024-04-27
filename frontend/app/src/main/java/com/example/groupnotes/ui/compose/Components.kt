package com.example.groupnotes.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.groupnotes.ui.compose.theme.LightBlue

@Preview(showBackground = true)
@Composable
fun GridItemPreview() {
    Box(
        Modifier
            .size(250.dp)
            .padding(16.dp)
    ) {
        GridItem(
            content = {
                Text("Hello")
            },
            onClick = {},
            contentTopEnd = {
                RoundedDropdownButton()
            },
            contentTopStart = {
                NumberIcon(3)
            }
        )
    }
}

@Composable
fun CustomToolbar(
    title: String,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        Modifier
            .height(64.dp)
            .fillMaxWidth()
            .background(Color.Blue.copy(0.1f))
            .padding(16.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if(onBackClick != null) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }

        ProvideTextStyle(value = MaterialTheme.typography.titleMedium) {
            Text(text = title)
        }
    }
}

@Composable
fun RoundedDropdownButton() {
    Box(
        modifier = Modifier
            .shadow(4.dp, CircleShape, clip = false)
            .background(LightBlue, RoundedCornerShape(100))
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
        }
    }
}

@Preview
@Composable
fun NumberIconPreview() {
    NumberIcon(number = 3)
}

@Composable
fun NumberIcon(number: Int) {
    Box(
        modifier = Modifier
            .scale(0.8f)
            .shadow(4.dp, CircleShape, clip = false)
            .background(Color.White, RoundedCornerShape(100))
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Text("$number")
        }
    }
}

@Composable
fun GridItem(
    content: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit,
    contentTopEnd: @Composable () -> Unit,
    contentTopStart: @Composable () -> Unit
) {
    val cornerShape = RoundedCornerShape(8.dp)
    val iconSize = 24.dp
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(120.dp)
        ,
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(iconSize / 3)
                .shadow(elevation = 8.dp, cornerShape, clip = false)
                .background(Color.White, cornerShape)
                .clickable(onClick = onClick)
            ,
            contentAlignment = Alignment.Center,
            content = { content() }
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(iconSize),
            contentAlignment = Alignment.Center
        ) {
            contentTopStart()
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(iconSize),
            contentAlignment = Alignment.Center
        ) {
            contentTopEnd()
        }
    }
}













