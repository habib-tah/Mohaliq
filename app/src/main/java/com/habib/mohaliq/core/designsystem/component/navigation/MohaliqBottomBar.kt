package com.habib.mohaliq.core.designsystem.component.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.theme.Dimens

private data class BottomBarItem(
    val labelRes: Int,
    val selectedIcon: Int,
    val unselectedIcon: Int
)

@Composable
fun MohaliqBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {

    val items = listOf(

        BottomBarItem(
            R.string.nav_home,
            R.drawable.ic_home,
            R.drawable.ic_home_outline
        ),

        BottomBarItem(
            R.string.nav_booking,
            R.drawable.ic_calendar_outline,
            R.drawable.ic_calendar_outline
        ),

        BottomBarItem(
            R.string.nav_explore,
            R.drawable.ic_routing,
            R.drawable.ic_routing_outline
        ),

        BottomBarItem(
            R.string.nav_favorite,
            R.drawable.ic_love,
            R.drawable.ic_love_outline
        ),

        BottomBarItem(
            R.string.nav_profile,
            R.drawable.ic_user,
            R.drawable.ic_user_outline
        )

    )

    NavigationBar {

        items.forEachIndexed { index, item ->

            NavigationBarItem(

                selected = selectedIndex == index,

                onClick = {
                    onItemSelected(index)
                },

                icon = {

                    Icon(

                        painter = painterResource(

                            if (selectedIndex == index)
                                item.selectedIcon
                            else
                                item.unselectedIcon

                        ),

                        contentDescription = null,

                        modifier = Modifier.size(Dimens.Space24)

                    )

                },

                label = {

                    Text(
                        text = stringResource(item.labelRes)
                    )

                }

            )

        }

    }

}