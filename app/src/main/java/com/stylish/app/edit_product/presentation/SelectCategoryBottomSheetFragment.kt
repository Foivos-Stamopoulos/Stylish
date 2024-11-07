package com.stylish.app.edit_product.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stylish.app.R
import com.stylish.app.core.presentation.util.ObserveAsEvents
import com.stylish.app.databinding.FragmentSelectCategoryBottomSheetBinding
import com.stylish.app.ui.theme.StylishTheme

class SelectCategoryBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: SelectCategoryViewModel by viewModels()
    private var _binding: FragmentSelectCategoryBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var categories: List<String>
    private lateinit var selectedCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categories = requireArguments().getStringArrayList(CATEGORIES_KEY)!!
        selectedCategory = requireArguments().getString(SELECTED_CATEGORY_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSelectCategoryBottomSheetBinding.bind(
            inflater.inflate(
                R.layout.fragment_select_category_bottom_sheet,
                container,
                false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                StylishTheme {

                    ObserveAsEvents(flow = viewModel.effect) { effect ->
                        when (effect) {
                            is SelectCategoryContract.Effect.SetCategory -> {
                                setFragmentResult(
                                    EditProductFragment.SELECT_CATEGORY_REQUEST_KEY,
                                    bundleOf(EditProductFragment.SELECTED_CATEGORY_KEY to effect.category)
                                )
                                dismiss()
                            }
                        }
                    }

                    val state = viewModel.state.collectAsState().value

                    SelectCategoryScreen(
                        state = state,
                        event = viewModel::onEvent
                    )
                }
            }
        }

        viewModel.start(categories, selectedCategory)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val CATEGORIES_KEY = "categories"
        private const val SELECTED_CATEGORY_KEY = "category"

        @JvmStatic
        fun newInstance(
            categories: List<String>,
            category: String
        ) = SelectCategoryBottomSheetFragment().apply {
            arguments = bundleOf(
                CATEGORIES_KEY to categories,
                SELECTED_CATEGORY_KEY to category
            )
        }
    }
}

@Composable
fun SelectCategoryScreen(
    state: SelectCategoryContract.State,
    event: (SelectCategoryContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(vertical = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp),
            text = stringResource(id = R.string.label_select_category),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(state.categories) { _, item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                        event(SelectCategoryContract.Event.OnCategorySelected(item))
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (state.selectedCategory == item) {
                        Image(
                            Icons.Rounded.Check,
                            contentDescription = stringResource(id = R.string.content_description_check),
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                        )
                    }

                }

            }
        }

    }
}

@Preview
@Composable
fun SelectCategoryScreenPreview() {
    val categories = listOf("electronics", "jewelery", "men's clothing", "women's clothing")
    StylishTheme {
        SelectCategoryScreen(
            state = (
                    SelectCategoryContract.State(categories = categories,
                        selectedCategory = "jewelery")
                    ),
            event = {}
        )
    }
}