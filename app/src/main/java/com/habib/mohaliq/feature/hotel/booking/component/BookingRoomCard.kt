package com.habib.mohaliq.feature.hotel.booking.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.habib.mohaliq.R
import com.habib.mohaliq.core.designsystem.component.MohaliqCard
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.core.model.RoomOption

@Composable
fun BookingRoomCard(
    room: RoomOption,
    expanded: Boolean,
    selected: Boolean,
    onExpand: () -> Unit,
    onBook: () -> Unit
) {

    MohaliqCard(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (selected)
                    Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(Dimens.Radius24)
                    )
                else Modifier
            )
            .clickable(onClick = onExpand),
        contentPadding = PaddingValues(0.dp)
    ) {

        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(12f / 3.5f)
                    .clip(RoundedCornerShape(Dimens.Radius24))
            ) {

                AsyncImage(
                    model = room.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

            }

            Column(
                modifier = Modifier.padding(Dimens.Space16)
            ) {

                Text(
                    text = room.title,
                    style = MaterialTheme.typography.titleMedium,
                )

                if (expanded) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(Dimens.Space8),
                        modifier = Modifier.padding(top = Dimens.Space8)
                    ) {

                        Text(
                            text = stringResource(
                                if (room.breakfastIncluded) {
                                    R.string.booking_breakfast_included
                                } else {
                                    R.string.booking_no_breakfast
                                }
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = room.cancellationPolicy,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "$${room.price}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Button(
                                onClick = onBook
                            ) {

                                Text(
                                    text = stringResource(
                                    if (selected) {
                                        R.string.booking_selected
                                    } else {
                                        R.string.booking_book_now
                                    }
                                    )
                                )

                            }

                        }

                    }

                }

            }

        }

    }

}
