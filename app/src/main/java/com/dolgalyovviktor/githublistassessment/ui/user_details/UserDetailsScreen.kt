package com.dolgalyovviktor.githublistassessment.ui.user_details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dolgalyovviktor.githublistassessment.R
import com.dolgalyovviktor.githublistassessment.common.res.Dimens
import com.dolgalyovviktor.githublistassessment.common.ui.rememberShimmerAnimation
import com.dolgalyovviktor.githublistassessment.presentation.user_details.UserDetailsAction
import com.dolgalyovviktor.githublistassessment.presentation.user_details.UserDetailsPresentationModel
import com.dolgalyovviktor.githublistassessment.presentation.user_details.UserDetailsViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

private const val STATE_CHANGE_ANIMATION_DURATION_MS = 1000

@Composable
fun UserDetailsScreen(
    username: String,
    modifier: Modifier = Modifier,
    viewModel: UserDetailsViewModel = getViewModel(
        parameters = { parametersOf(username) }
    )
) {
    Scaffold(
        topBar = {
            UserDetailsAppBar(
                username = username,
                onBackClick = {
                    viewModel.dispatch(UserDetailsAction.BackClick)
                })
        }
    ) { paddingValues ->
        UserDetailsContent(
            model = viewModel.model.collectAsState().value,
            modifier = modifier.padding(paddingValues),
            onRetryClick = { viewModel.dispatch(UserDetailsAction.RetryClick) }
        )
    }
}

@Composable
private fun UserDetailsAppBar(
    username: String,
    onBackClick: () -> Unit
) = TopAppBar(
    title = { Text(text = username) },
    navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.content_description_back)
            )
        }
    }
)

@Composable
private fun UserDetailsContent(
    model: UserDetailsPresentationModel?,
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) = AnimatedContent(
    targetState = model,
    label = "user_details_content",
    transitionSpec = {
        fadeIn(tween(STATE_CHANGE_ANIMATION_DURATION_MS)) togetherWith
                fadeOut(tween(STATE_CHANGE_ANIMATION_DURATION_MS))
    }
) { renderModel ->
    when (renderModel) {
        null, UserDetailsPresentationModel.Loading -> UserDetailsProgress(modifier)
        is UserDetailsPresentationModel.User -> UserDetailsData(
            user = renderModel,
            modifier = modifier
        )

        UserDetailsPresentationModel.Error -> UserDetailsRetry(
            modifier = modifier,
            onRetryClick = onRetryClick
        )
    }
}

@Composable
private fun UserDetailsData(
    user: UserDetailsPresentationModel.User,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(
            horizontal = Dimens.padding,
            vertical = Dimens.padding
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = stringResource(id = R.string.content_description_user_avatar),
                modifier = Modifier
                    .size(Dimens.avatarSizeLarge)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(Dimens.padding))
            Column(modifier = Modifier.weight(1f)) {
                if (user.company.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.template_company, user.company),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (user.location.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.template_location, user.location),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (user.email.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.template_email, user.email),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(Dimens.padding))
        if (user.bio.isNotEmpty()) {
            Text(
                text = stringResource(R.string.template_bio, user.bio),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(Dimens.padding))
        Row {
            Text(
                text = stringResource(R.string.template_followers, user.followersCount),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.width(Dimens.padding))
            Text(
                text = stringResource(R.string.template_following, user.followingCount),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun UserDetailsProgress(modifier: Modifier = Modifier) {
    val shimmerBrush = rememberShimmerAnimation()
    Column(modifier = modifier.padding(Dimens.padding)) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(Dimens.avatarSizeLarge)
                    .clip(CircleShape)
                    .background(shimmerBrush)
            )
            Spacer(modifier = Modifier.width(Dimens.padding))
            Column {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.typography.bodyMedium.lineHeight.value.dp)
                            .background(shimmerBrush)
                    )
                    Spacer(modifier = Modifier.height(Dimens.halfPadding))
                }
            }
        }
        Spacer(modifier = Modifier.height(Dimens.padding))
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(MaterialTheme.typography.labelMedium.lineHeight.value.dp)
                    .background(shimmerBrush)
            )
            Spacer(modifier = Modifier.width(Dimens.halfPadding))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(MaterialTheme.typography.labelMedium.lineHeight.value.dp)
                    .background(shimmerBrush)
            )
        }
        Spacer(modifier = Modifier.height(Dimens.padding))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.typography.bodyMedium.fontSize.value.dp * 2)
                .background(shimmerBrush)
        )
    }
}

@Composable
private fun UserDetailsRetry(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(Dimens.padding),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Image(
        imageVector = Icons.Rounded.Warning,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
        contentDescription = stringResource(id = R.string.content_description_retry),
        modifier = Modifier.size(Dimens.errorIconSizeLarge)
    )
    Text(
        text = stringResource(id = R.string.label_error),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = Dimens.halfPadding)
    )
    FilledTonalButton(
        onClick = onRetryClick,
        modifier = Modifier.padding(vertical = Dimens.halfPadding)
    ) {
        Text(text = stringResource(id = R.string.action_retry))
    }
}

@Preview(showBackground = true)
@Composable
private fun UserDetailsDataPreview() {
    UserDetailsData(
        user = UserDetailsPresentationModel.User(
            username = "octocat",
            avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4",
            company = "GitHub",
            location = "San Francisco",
            bio = "GitHub mascot",
            email = "octocat@github.com",
            followersCount = 320,
            followingCount = 280
        ),
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun UserDetailsProgressPreview() = UserDetailsProgress()

@Preview(showBackground = true)
@Composable
private fun UserDetailsRetryPreview() = UserDetailsRetry(
    onRetryClick = {}
)