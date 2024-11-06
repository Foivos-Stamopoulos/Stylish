package com.stylish.app.product_detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.stylish.app.R
import com.stylish.app.core.domain.model.Product
import com.stylish.app.core.presentation.util.CurrencyFormatter
import com.stylish.app.core.presentation.util.DimensionHelper
import com.stylish.app.core.presentation.util.getFragmentAnimationNavOptions
import com.stylish.app.core.presentation.util.showSnackBar
import com.stylish.app.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ProductDetailFragment()
    }

    private val viewModel: ProductDetailViewModel by viewModels()
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.bind(inflater.inflate(R.layout.fragment_product_detail, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
        setupToolbar()
        viewModel.start()
    }

    private fun subscribe() {

        viewModel.isLoading.observe(viewLifecycleOwner) {
            val isLoading = it.getContentIfNotHandled()
            isLoading?.let {
                binding.loaderLayout.loader.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        viewModel.product.observe(viewLifecycleOwner) {
            setupProductDetails(it)
        }

        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            val uiText = it.getContentIfNotHandled()
            uiText?.let {
                showSnackBar(uiText.asString(requireContext()), binding.root)
            }
        }

        viewModel.openEditProductScreen.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { product ->
                openEditProductScreen(product)
            }
        }
    }

    private fun setupProductDetails(product: Product) {
        setupImage(product.image)
        binding.titleTv.text = product.title
        binding.categoryTv.text = product.category
        binding.priceTv.text = CurrencyFormatter.formatPrice(product.price)
        setupDescription(product.description)
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

        binding.toolbar.inflateMenu(R.menu.menu_product_detail)
        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item?.itemId == R.id.action_edit) {
                viewModel.onEditProductClick()
            }
            true
        }
    }

    private fun openEditProductScreen(product: Product) {
        findNavController().navigate(
            ProductDetailFragmentDirections.actionProductDetailFragmentToEditProductFragment(product),
            getFragmentAnimationNavOptions()
        )
    }

    private fun setupDescription(text: String) {
        binding.truncatedProductDescriptionTv.text = text
        binding.fullProductDescriptionTv.text = text

        binding.moreTv.setOnClickListener {
            if (binding.fullProductDescriptionTv.visibility == View.GONE) {
                binding.truncatedProductDescriptionTv.visibility = View.GONE
                binding.fullProductDescriptionTv.visibility = View.VISIBLE
                binding.moreTv.text = getString(R.string.label_less)
            } else {
                binding.truncatedProductDescriptionTv.visibility = View.VISIBLE
                binding.fullProductDescriptionTv.visibility = View.GONE
                binding.moreTv.text = getString(R.string.label_more)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}