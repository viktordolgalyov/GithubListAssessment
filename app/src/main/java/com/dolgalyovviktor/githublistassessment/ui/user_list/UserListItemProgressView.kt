package com.dolgalyovviktor.githublistassessment.ui.user_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dolgalyovviktor.githublistassessment.common.ui.rememberShimmerAnimation
import com.dolgalyovviktor.githublistassessment.common.res.Dimens

@Composable
fun UserListItemProgressView(
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    descriptionStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val shimmerBrush = rememberShimmerAnimation()

    ElevatedCard(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(Dimens.avatarSizeMedium)
                    .padding(
                        top = Dimens.quarterPadding,
                        bottom = Dimens.quarterPadding,
                        start = Dimens.halfPadding
                    )
                    .clip(CircleShape)
                    .background(brush = shimmerBrush)
            )
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = Dimens.padding),
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(titleStyle.fontSize.value.dp)
                        .background(brush = shimmerBrush)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(descriptionStyle.fontSize.value.dp)
                        .padding(top = Dimens.quarterPadding)
                        .background(brush = shimmerBrush)
                )
            }
        }
    }
}

@Preview
@Composable
private fun UserListItemProgressViewPreview() = UserListItemProgressView()