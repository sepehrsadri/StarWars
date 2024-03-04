package com.sadri.search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.sadri.model.AppException
import com.sadri.testing.HiltComponentActivity
import com.sadri.testing.util.waitUntilExists
import com.sadri.testing.util.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.sadri.designsystem.R as DR

@HiltAndroidTest
class SearchScreenTest {

  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

  private val context by lazy {
    composeTestRule.activity
  }

  @Before
  fun setup() {
    hiltRule.inject()
  }

  @Test
  fun givenInitialEmptyState_thenNoResultImageDisplayed() {
    // Given
    val contentDescription = context.getString(R.string.no_result_description)

    // Then
    composeTestRule.onNodeWithContentDescription(contentDescription)
      .assertIsDisplayed()
  }

  @Test
  fun givenInitialEmptyState_thenSearchTextFieldDisplayed() {
    // Given
    val searchHint = context.getString(R.string.search_hint)

    // Then
    composeTestRule.onNodeWithText(searchHint)
      .assertIsDisplayed()
  }

  @Test
  fun givenSearchText_thenProgressBarDisplayed() {
    // Given
    val progressBarTestTag = context.getString(DR.string.progress_bar_test_tag)
    val textFieldTestTag = context.getString(R.string.search_text_filed_test_tag)

    // When
    composeTestRule.onNodeWithTag(textFieldTestTag).performTextInput("a")

    // Then
    composeTestRule.waitUntilExists(hasTestTag(progressBarTestTag))
    composeTestRule.onNodeWithTag(progressBarTestTag)
      .assertIsDisplayed()
  }

  @Test
  fun givenSearchText_thenSpecificItemExistOnList() {
    // Given
    val textFieldTestTag = context.getString(R.string.search_text_filed_test_tag)
    val resultItem = "Luke Skywalker"

    // When
    composeTestRule.onNodeWithTag(textFieldTestTag).performTextInput("a")

    // Then
    composeTestRule.waitUntilExists(hasText(resultItem))
    composeTestRule.onNodeWithText(resultItem)
      .assertIsDisplayed()
  }

  @Test
  fun givenInvalidSearchText_thenErrorTextNoItemsShown() {
    // Given
    val textFieldTestTag = context.getString(R.string.search_text_filed_test_tag)
    val errorMessage = AppException.Empty.message!!

    // When
    composeTestRule.onNodeWithTag(textFieldTestTag).performTextInput("---")

    // Then
    composeTestRule.waitUntilExists(hasText(errorMessage))
    composeTestRule.onNodeWithText(errorMessage)
      .assertIsDisplayed()
  }

  @Test
  fun givenSearchAfterError_thenResultChangeWithSpecificItem() {
    // Given
    val textFieldTestTag = context.getString(R.string.search_text_filed_test_tag)
    val errorMessage = AppException.Empty.message!!
    val resultItem = "Luke Skywalker"

    // When
    composeTestRule.onNodeWithTag(textFieldTestTag).performTextInput("---")
    composeTestRule.waitUntilExists(hasText(errorMessage))
    composeTestRule.onNodeWithTag(textFieldTestTag).performTextClearance()
    composeTestRule.onNodeWithTag(textFieldTestTag).performTextInput("a")

    // Then
    composeTestRule.waitUntilExists(hasText(resultItem))
    composeTestRule.onNodeWithText(resultItem)
      .assertIsDisplayed()
  }


  @Test
  fun givenSearchTextAndScrollToEnd_thenPaginationWillLoadNewItems() {
    // Given
    val textFieldTestTag = context.getString(R.string.search_text_filed_test_tag)
    val lazyColumnTestTag = context.getString(R.string.search_lazy_column_test_tag)
    val paginationNewItem = "Wicket Systri Warrick"

    // When
    composeTestRule.onNodeWithTag(textFieldTestTag).performTextInput("a")
    composeTestRule.waitUntilTimeout(8_000)
    composeTestRule
      .onNodeWithTag(lazyColumnTestTag)
      .performScrollToIndex(19)


    // Then
    composeTestRule.waitUntilExists(hasText(paginationNewItem))
    composeTestRule.onNodeWithText(paginationNewItem)
      .assertIsDisplayed()
  }
}