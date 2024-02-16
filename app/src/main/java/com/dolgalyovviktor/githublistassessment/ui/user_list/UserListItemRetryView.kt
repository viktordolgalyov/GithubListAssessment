package com.dolgalyovviktor.githublistassessment.ui.user_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dolgalyovviktor.githublistassessment.R
import com.dolgalyovviktor.githublistassessment.common.res.Dimens

@Composable
fun UserListItemRetryView(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) = ElevatedCard(modifier = modifier.fillMaxWidth()) {
    Image(
        imageVector = Icons.Rounded.Warning,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
        contentDescription = stringResource(id = R.string.content_description_retry),
        modifier = Modifier
            .size(Dimens.errorIconSizeMedium)
            .padding(top = Dimens.halfPadding)
            .align(Alignment.CenterHorizontally)
    )
    Text(
        text = stringResource(id = R.string.label_error),
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(
                top = Dimens.halfPadding,
                start = Dimens.padding,
                end = Dimens.padding
            )
    )
    FilledTonalButton(
        onClick = onRetryClick,
        modifier = Modifier
            .padding(vertical = Dimens.halfPadding)
            .align(Alignment.CenterHorizontally)
    ) {
        Text(text = stringResource(id = R.string.action_retry))
    }
}

@Composable
@Preview(showBackground = true)
private fun UserListItemRetryViewPreview() = UserListItemRetryView(
    onRetryClick = {}
)