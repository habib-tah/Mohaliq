package com.habib.mohaliq.feature.hotel.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import com.habib.mohaliq.core.designsystem.component.MohaliqChip
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.hotel.model.RoomItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomChipGroup(
    rooms: List<RoomItem>,
    onRoomSelected: (RoomItem) -> Unit
) {

    FlowRow(

        horizontalArrangement = Arrangement.spacedBy(Dimens.Space8),
        verticalArrangement = Arrangement.spacedBy(Dimens.Space8)

    ) {

        rooms.forEach { room ->

            MohaliqChip(

                text = room.title,

                selected = room.selected,

                onClick = {

                    onRoomSelected(room)

                }

            )

        }

    }

}