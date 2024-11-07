package com.stylish.app.edit_product.presentation

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.stylish.app.R
import com.stylish.app.core.domain.model.Product
import com.stylish.app.core.presentation.util.DimensionHelper
import com.stylish.app.core.presentation.util.MyTextWatcher
import com.stylish.app.core.presentation.util.checkConnection
import com.stylish.app.core.presentation.util.showSnackBar
import com.stylish.app.databinding.FragmentEditProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProductFragment : Fragment() {

    companion object {
        fun newInstance() = EditProductFragment()
        private const val SELECT_CATEGORY_BOTTOM_SHEET_TAG = "select_category"
        const val SELECT_CATEGORY_REQUEST_KEY = "select_category_request_key"
        const val SELECTED_CATEGORY_KEY = "selected_category_key"
    }

    private val viewModel: EditProductViewModel by viewModels()
    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(SELECT_CATEGORY_REQUEST_KEY) { key, bundle ->
            if (key == SELECT_CATEGORY_REQUEST_KEY) {
                if (bundle.containsKey(SELECTED_CATEGORY_KEY)) {
                    val category = bundle.getString(SELECTED_CATEGORY_KEY, null)
                    viewModel.onCategorySelected(category)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProductBinding.bind(inflater.inflate(R.layout.fragment_edit_product, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupClickListeners()
        setupTextWatchers()
        subscribe()
    }

    private fun subscribe() {

        viewModel.productLD.observe(viewLifecycleOwner) { product ->
            setupUI(product)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { isLoading ->
                setLoadingState(isLoading)
            }
        }

        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            val uiText = it.getContentIfNotHandled()
            uiText?.let {
                showSnackBar(uiText.asString(requireContext()), binding.root)
            }
        }

        viewModel.openSelectCategoryBottomSheet.observe(viewLifecycleOwner) {
            openSelectCategoryBottomSheet(it.categories, it.selectedCategory)
        }

        viewModel.titleError.observe(viewLifecycleOwner) { uiText ->
            if (uiText == null) {
                binding.titleLayout.error = null
            } else {
                binding.titleLayout.error = uiText.asString(requireContext())
            }
        }

        viewModel.priceError.observe(viewLifecycleOwner) { uiText ->
            if (uiText == null) {
                binding.priceLayout.error = null
            } else {
                binding.priceLayout.error = uiText.asString(requireContext())
            }
        }

        viewModel.category.observe(viewLifecycleOwner) {
            binding.categoryEt.setText(it)
        }

        viewModel.categoryError.observe(viewLifecycleOwner) { uiText ->
            if (uiText == null) {
                binding.categoryLayout.error = null
            } else {
                binding.categoryLayout.error = uiText.asString(requireContext())
            }
        }

        viewModel.descriptionError.observe(viewLifecycleOwner) { uiText ->
            if (uiText == null) {
                binding.descriptionLayout.error = null
            } else {
                binding.descriptionLayout.error = uiText.asString(requireContext())
            }
        }
    }

    private fun setupUI(product: Product) {
        setupImage(product.image)
        binding.titleEt.setText(product.title)
        binding.priceEt.setText(product.price.toString())
        binding.categoryEt.setText(product.category)
        binding.descriptionEt.setText(product.description)
    }

    private fun setupImage(imageUrl: String) {
        val width = DimensionHelper.dpToPx(
            DimensionHelper.getFullScreenWidth().toFloat(),
            requireContext()) - (resources.getDimension(R.dimen.content_padding) * 2).toInt()
        val height = DimensionHelper.dpToPx(213f, requireContext())

        val placeholderDrawable = ResourcesCompat.getDrawable(resources, R.drawable.product_image_placeholder, null)
        placeholderDrawable!!.setBounds(0,0, width, height)

        Glide.with(this)
            .load(imageUrl)
            .override(width, height)
            .placeholder(placeholderDrawable)
            .fitCenter()
            .into(binding.imageIv)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupClickListeners() {
        binding.categoryEt.setOnClickListener {
            viewModel.onSelectCategoryPressed()
        }
        binding.saveBtn.setOnClickListener {
            checkConnection(
                {
                    viewModel.validateData(
                        binding.titleEt.text.toString(),
                        binding.priceEt.text.toString(),
                        binding.categoryEt.text.toString(),
                        binding.descriptionEt.text.toString()
                    )
                },
                binding.root
            )
        }
    }

    private fun setupTextWatchers() {
        binding.titleEt.addTextChangedListener(titleTextWatcher)
        binding.priceEt.addTextChangedListener(priceTextWatcher)
        binding.categoryEt.addTextChangedListener(categoryTextWatcher)
        binding.descriptionEt.addTextChangedListener(descriptionTextWatcher)
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.loaderLayout.loader.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.titleEt.isEnabled = !isLoading
        binding.priceEt.isEnabled = !isLoading
        binding.categoryLayout.isEnabled = !isLoading
        binding.descriptionEt.isEnabled = !isLoading
        binding.saveBtn.isEnabled = !isLoading
    }

    private fun openSelectCategoryBottomSheet(
        categories: ArrayList<String>,
        selectedCategory: String
    ) {
        val dialog = SelectCategoryBottomSheetFragment.newInstance(
            categories,
            selectedCategory
        )
        dialog.show(parentFragmentManager, SELECT_CATEGORY_BOTTOM_SHEET_TAG)
    }

    private val titleTextWatcher = object : MyTextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            viewModel.onTitleChanged()
        }
    }

    private val priceTextWatcher = object : MyTextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            viewModel.onPriceChanged()
        }
    }

    private val categoryTextWatcher = object : MyTextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            viewModel.onCategoryChanged()
        }
    }

    private val descriptionTextWatcher = object : MyTextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            viewModel.onDescriptionChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}