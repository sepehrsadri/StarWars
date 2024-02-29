package com.sadri.search

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadri.designsystem.component.AnimationState
import com.sadri.designsystem.component.Error
import com.sadri.designsystem.component.Loading
import com.sadri.designsystem.component.OnLifecycleEvent
import com.sadri.designsystem.component.shakeKeyframes
import com.sadri.designsystem.theme.space
import com.sadri.model.PeopleEntity
import kotlinx.coroutines.launch


@Composable
fun SearchRoute(
  modifier: Modifier = Modifier,
  viewModel: SearchViewModel = hiltViewModel()
) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle()

  SearchScreen(
    modifier = modifier,
    uiState = uiState.value,
    onValueChanged = viewModel::setSearchText,
    onSubmitSearchClicked = viewModel::search,
    retry = viewModel::onRetry
  )
}

@Composable
private fun SearchScreen(
  modifier: Modifier,
  uiState: SearchUiState,
  onValueChanged: (String) -> Unit,
  onSubmitSearchClicked: (String) -> Unit,
  retry: () -> Unit
) {
  val focusRequester = remember { FocusRequester() }
  val keyboardController = LocalSoftwareKeyboardController.current
  Scaffold(
    modifier = modifier,
    topBar = {
      SearchBar(
        modifier = Modifier.focusRequester(focusRequester),
        defaultText = "",
        onValueChanged = onValueChanged,
        onSubmitSearchClicked = onSubmitSearchClicked
      )
    }
  ) { contentPadding ->
    val innerModifier = Modifier.padding(contentPadding)

    LaunchedEffect(uiState) {
      if (uiState == SearchUiState.Loading) {
        keyboardController?.hide()
      }
    }

    Box(
      modifier = innerModifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background)
        .padding(MaterialTheme.space.medium)
    ) {
      when (uiState) {
        is SearchUiState.Error -> {
          Error(
            message = requireNotNull(uiState.error.message),
            retry = retry
          )
        }
        SearchUiState.Loading -> {
          Loading()
        }
        is SearchUiState.Success -> {
          PeopleList(
            items = uiState.result.results,
            modifier = innerModifier
          )
        }
        SearchUiState.Nothing -> {
          EmptyState()
        }
      }
    }
  }
}

@Composable
private fun BoxScope.EmptyState() {
  Image(
    modifier = Modifier.align(Alignment.Center),
    painter = painterResource(id = R.drawable.ic_no_result),
    contentDescription = "Login icon"
  )
}

@Composable
private fun PeopleList(
  items: List<PeopleEntity>,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier,
    contentPadding = PaddingValues(MaterialTheme.space.medium)
  )
  {
    items.forEach { todoItem ->
      val todoItemId = todoItem.name
      item(key = todoItemId) {
        PeopleItem(todoItem)
      }
    }
  }
}

@Composable
private fun PeopleItem(
  peopleItem: PeopleEntity
) {
  Spacer(modifier = Modifier.height(MaterialTheme.space.small))
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(4.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.onSurface
    ),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
  ) {
    Text(
      text = peopleItem.name,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      color = MaterialTheme.colorScheme.outline,
      modifier = Modifier.padding(MaterialTheme.space.xSmall)
    )
  }
}

@Composable
private fun SearchBar(
  modifier: Modifier = Modifier,
  onValueChanged: (String) -> Unit,
  onSubmitSearchClicked: (String) -> Unit,
  defaultText: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = MaterialTheme.space.medium),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.space.medium)
  ) {
    SearchBox(
      modifier = modifier,
      defaultText = defaultText,
      editable = true,
      onValueChanged = onValueChanged,
      onSubmitSearchClicked = onSubmitSearchClicked
    )
  }
}

@Composable
private fun SearchBox(
  modifier: Modifier = Modifier,
  editable: Boolean,
  defaultText: String,
  onValueChanged: (String) -> Unit = {},
  onSubmitSearchClicked: (String) -> Unit = {},
  onBoxClicked: ((String) -> Unit)? = null,
  animationState: AnimationState = AnimationState()
) {
  val textFieldState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
    mutableStateOf(
      TextFieldValue(
        text = defaultText,
        selection = TextRange(defaultText.length)
      )
    )
  }

  OnLifecycleEvent { _, event ->
    when (event) {
      Lifecycle.Event.ON_RESUME -> {
        if (textFieldState.value.text.isEmpty()) {
          animationState.startAnimation()
        }
      }
      else -> {
        //no-op
      }
    }
  }

  val onSubmitClickHandler = remember {
    {
      onSubmitSearchClicked.invoke(textFieldState.value.text)
    }
  }

  Row(
    modifier = Modifier.boxModifier(onBoxClicked, textFieldState),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.space.small)
  ) {
    Icon(
      modifier = Modifier
        .clickable { onSubmitClickHandler.invoke() }
        .padding(MaterialTheme.space.xSmall),
      painter = painterResource(id = R.drawable.ic_search),
      tint = MaterialTheme.colorScheme.onBackground,
      contentDescription = stringResource(R.string.search_content_description),
    )

    Box {
      BasicTextField(
        modifier = modifier.fillMaxWidth(),
        value = textFieldState.value,
        onValueChange = {
          textFieldState.value = it
          onValueChanged(it.text)
        },
        textStyle = MaterialTheme.typography.labelLarge.copy(
          color = MaterialTheme.colorScheme.onBackground
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        enabled = editable,
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Text,
          imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions {
          onSubmitClickHandler.invoke()
        }
      )

      if (textFieldState.value.text.isEmpty()) {
        HintText(stringResource(R.string.search_hint), animationState)
      }
    }
  }
}

@Composable
private fun HintText(textHint: String, animationState: AnimationState) {

  val runAnimationState = animationState.runAnimationState.collectAsState()
  val coroutineScope = rememberCoroutineScope()
  val offsetX = remember { Animatable(0f) }

  LaunchedEffect(key1 = runAnimationState.value) {
    if (runAnimationState.value) {
      coroutineScope.launch {
        offsetX.animateTo(
          targetValue = 0f,
          animationSpec = shakeKeyframes
        )
        animationState.onAnimationEnd()
      }
    }
  }

  Text(
    modifier = Modifier
      .padding(horizontal = MaterialTheme.space.tiny)
      .offset { IntOffset(offsetX.value.toInt(), 0) },
    text = textHint,
    style = MaterialTheme.typography.labelLarge
  )
}

@Composable
private fun Modifier.boxModifier(
  onBoxClicked: ((String) -> Unit)?,
  textFieldState: State<TextFieldValue>
): Modifier = composed {

  val clickModifier = onBoxClicked?.let {
    clickable {
      onBoxClicked.invoke(textFieldState.value.text)
    }
  } ?: run { this }

  padding(vertical = MaterialTheme.space.xSmall)
    .fillMaxWidth()
    .height(48.dp)
    .clip(MaterialTheme.shapes.large)
    .then(clickModifier)
    .background(MaterialTheme.colorScheme.surface)
    .padding(MaterialTheme.space.small)
}