package com.alerdoci.marvelsuperheroes.app.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.screens.home.composable.SuperheroItem
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import kotlin.math.abs
import kotlin.math.roundToInt

//@Preview
//@Composable
//fun Call(){
//
//    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
//    val configuration = LocalConfiguration.current
//    val maxWidth = configuration.screenWidthDp.dp
//
//    Carousel(
//        list,
//        count = list.size,
//        contentWidth = maxWidth,
//        contentHeight = 200.dp,
//        content = { modifier, index ->
//            Card(modifier = modifier,
//            ){
//                Text(
//                    text = list[index].toString(),
//                    textAlign = TextAlign.Center,
//                    fontSize = 50.sp,
//                    color = Color.Black,
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//        }
//    )
//}

@Composable
fun Carousel(
    count: Int,
    parentModifier: Modifier = Modifier
        .fillMaxWidth()
        .height(540.dp),
    contentWidth: Dp,
    contentHeight: Dp,
    content: @Composable (modifier: Modifier, index: Int) -> Unit
) {
    val listState = rememberLazyListState(Int.MAX_VALUE / 2)

    BoxWithConstraints(
        modifier = parentModifier
    ) {
        val halfRowWidth = constraints.maxWidth / 2

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(-contentHeight / 2),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                count = Int.MAX_VALUE,
                itemContent = { globalIndex ->
                    val scale by remember {
                        derivedStateOf {
                            val currentItem = listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == globalIndex } ?: return@derivedStateOf 0.85f

                            (1f - minOf(1f, abs(currentItem.offset + (currentItem.size / 2) - halfRowWidth).toFloat() / halfRowWidth) * 0.25f)
                        }
                    }

                    content(
                        index = globalIndex % count,
                        modifier = Modifier
                            .width(contentWidth)
                            .height(contentHeight)
                            .scale(scale)
                            .zIndex(scale * 10)
                    )
                }
            )
        }
    }
}
@Preview
@Composable
fun CarouselPreview(){
    Carousel(
        count = 5,
        contentWidth = 500.dp,
        contentHeight = 200.dp,
        content = { modifier, index ->
            Card {
                Text(text = "hey")
            }
        }
    )
}
//
//@Composable
//fun Carousel(
//    list: List<Int>,
//    count: Int,
//    parentModifier: Modifier = Modifier
//        .fillMaxWidth()
//        .height(540.dp)
//        .background(Color.Red),
//    contentWidth: Dp,
//    contentHeight: Dp,
//    content: @Composable (modifier: Modifier, index: Int) -> Unit
//) {
//    val listState = rememberLazyListState(50)
//    val context = LocalContext.current
//
//    BoxWithConstraints(
//        modifier = parentModifier
//    ) {
//        val halfRowWidth = constraints.maxWidth / 2
//
//        LazyColumn(
//            state = listState,
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(top = 100.dp, end = 100.dp),  //пространство между последним item и границей
//            verticalArrangement = Arrangement.spacedBy(0.dp), //пространство между items
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            itemsIndexed(list) { index, item ->
//
//
//
////                count = count,
////                itemContent = { globalIndex ->
////                    val stateItem: StateListItem by remember {
////                        derivedStateOf {
////                            val currentItem = listState.layoutInfo.visibleItemsInfo
////                                .firstOrNull { it.index == globalIndex } ?: return@derivedStateOf StateListItem(12.dp,0.85f)
////
////                            Log.d("ccccccccccccTAG", "globalIndex: $globalIndex")
////
////
////                            val scale = (1f - minOf
////                                (1f, abs
////                                    (currentItem.offset + (currentItem.size / 2)
////                                    - halfRowWidth).toFloat() / halfRowWidth)
////                                    * 0.25f)
////                            StateListItem(12.dp, scale)
////                        }
////                    }
////
////
//                Log.d("TAG", "Carousel: ")
//                val stateItem: StateListItem by remember {
//                    derivedStateOf {
//                        StateListItem((index * 100).dp, 1f)
//                    }
//                }
//
//
//
//
//                content(
//                    index = index,
//                    modifier = Modifier
//                        .width(contentWidth)
//                        .height(contentHeight)
//                        .offset(y = stateItem.offsetY)
////                            .scale(stateItem.scale)
//                        .zIndex(stateItem.scale * 10) //порядок рисования children
//                        .border(width = 5.dp, color = Color.Blue)
////                            .draggable()
//
//                )
////                }
//            }
//        }
//    }
//}
//
//data class StateListItem(
//    val offsetY: Dp,
//    val scale: Float
//)
