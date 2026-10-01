package com.habib.mohaliq.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.habib.mohaliq.R
import com.habib.mohaliq.app.navigation.Destinations
import com.habib.mohaliq.core.designsystem.component.MohaliqCard
import com.habib.mohaliq.core.designsystem.component.MohaliqScaffold
import com.habib.mohaliq.core.designsystem.component.MohaliqTopBar
import com.habib.mohaliq.core.designsystem.component.navigation.MohaliqBottomBar
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.profile.model.ProfileMenuItem


private const val PROFILE_IMAGE_URL =
    "https://images.unsplash.com/photo-1692029861107-991b13db6ad0?w=200&q=80&auto=format&fit=crop"
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onAction: (ProfileAction) -> Unit,
    onBottomNavigation: (Destinations) -> Unit
) {

    MohaliqScaffold(

        topBar = {
            MohaliqTopBar(title = stringResource(R.string.nav_profile))
        },

        bottomBar = {

            MohaliqBottomBar(

                selectedIndex = uiState.selectedBottomBarIndex,

                onItemSelected = { index ->

                    onAction(ProfileAction.BottomBarItemSelected(index))

                    when (index) {
                        0 -> onBottomNavigation(Destinations.Home)
                        1 -> onBottomNavigation(Destinations.History)
                        2 -> onBottomNavigation(Destinations.Explore)
                        3 -> onBottomNavigation(Destinations.Favorites)
                        4 -> onBottomNavigation(Destinations.Profile)
                    }

                }

            )

        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(
                horizontal = Dimens.Space20,
                vertical = Dimens.Space16
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space12)
        ) {

            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimens.Space8),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    AsyncImage(
                        model = PROFILE_IMAGE_URL,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.padding(start = Dimens.Space16)
                    ) {

                        Text(
                            text = uiState.userName,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = uiState.userEmail,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                    }

                }

            }

            items(
                items = uiState.menuItems,
                key = { it.id }
            ) { menuItem ->

                ProfileMenuRow(
                    item = menuItem,
                    onClick = { onAction(ProfileAction.MenuItemClicked(menuItem.id)) }
                )

            }

        }

    }

    if (uiState.showLogoutConfirmation) {

        AlertDialog(
            onDismissRequest = { onAction(ProfileAction.LogoutDismissed) },
            title = { Text(stringResource(R.string.profile_logout)) },
            text = { Text(stringResource(R.string.profile_logout_message)) },
            confirmButton = {
                TextButton(onClick = { onAction(ProfileAction.LogoutConfirmed) }) {
                    Text(stringResource(R.string.profile_logout))
                }
            },
            dismissButton = {
                TextButton(onClick = { onAction(ProfileAction.LogoutDismissed) }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )

    }

}

@Composable
private fun ProfileMenuRow(
    item: ProfileMenuItem,
    onClick: () -> Unit
) {

    val contentColor =
        if (item.isDestructive)
            MaterialTheme.colorScheme.error
        else
            MaterialTheme.colorScheme.onSurface

    MohaliqCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Space12)
        ) {

            Icon(
                painter = painterResource(item.icon),
                contentDescription = null,
                tint = contentColor
            )

            Text(
                text = stringResource(item.labelRes),
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor,
                modifier = Modifier.weight(1f)
            )

            if (!item.isDestructive) {

                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right_outline),
                    contentDescription = null
                )

            }

        }

    }

}
