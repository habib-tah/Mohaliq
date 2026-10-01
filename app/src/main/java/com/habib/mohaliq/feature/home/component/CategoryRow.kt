package com.habib.mohaliq.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.habib.mohaliq.core.designsystem.component.MohaliqChip
import com.habib.mohaliq.core.designsystem.theme.Dimens
import com.habib.mohaliq.feature.home.model.CategoryItem
import androidx.compose.foundation.lazy.items

@Composable
fun CategoryRow(
    categories: List<CategoryItem>,
    onCategoryClick: (CategoryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.Space20),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Space8)
    ) {

        items(categories) { category ->

            MohaliqChip(
                text = stringResource(category.labelRes),
                iconRes = category.iconRes,
                onClick = {
                    onCategoryClick(category)
                }
            )

        }

    }

}