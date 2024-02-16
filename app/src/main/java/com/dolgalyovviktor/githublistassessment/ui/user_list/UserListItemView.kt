package com.dolgalyovviktor.githublistassessment.ui.user_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.dolgalyovviktor.githublistassessment.domain.model.UserId
import com.dolgalyovviktor.githublistassessment.common.res.Dimens
import com.dolgalyovviktor.githublistassessment.R

@Composable
fun UserListItemView(
    model: UserRenderModel,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    descriptionStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onClick: ((UserId) -> Unit)? = null
) = ElevatedCard(
    onClick = { onClick?.invoke(model.id) },
    modifier = modifier.fillMaxWidth()
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = model.avatarUrl,
            contentDescription = stringResource(id = R.string.content_description_user_avatar),
            modifier = Modifier
                .size(Dimens.avatarSizeMedium)
                .padding(
                    top = Dimens.quarterPadding,
                    bottom = Dimens.quarterPadding,
                    start = Dimens.halfPadding
                )
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = Dimens.padding),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = model.username,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = titleStyle
            )
            Text(
                modifier = Modifier.padding(
                    top = Dimens.quarterPadding
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = model.description,
                style = descriptionStyle
            )
        }
    }
}

@Preview
@Composable
fun UserListItemPreview() = UserListItemView(
    model = UserRenderModel(
        id = UserId(0),
        username = "username",
        description = "Description",
        avatarUrl = ""
    ),
    onClick = null
)

data class UserRenderModel(
    val id: UserId,
    val username: String,
    val description: String,
    val avatarUrl: String
)