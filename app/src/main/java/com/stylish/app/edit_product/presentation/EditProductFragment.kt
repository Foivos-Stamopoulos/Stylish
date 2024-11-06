package com.stylish.app.edit_product.presentation

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
import com.stylish.app.core.presentation.util.DimensionHelper
import com.stylish.app.databinding.FragmentEditProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProductFragment : Fragment() {

    companion object {
        fun newInstance() = EditProductFragment()
    }

    private val viewModel: EditProductViewModel by viewModels()
    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!

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
        subscribe()
    }

    private fun subscribe() {

        viewModel.productLD.observe(viewLifecycleOwner) { product ->
            setupUI(product)
        }
    }

    private fun setupUI(product: Product) {
        setupImage(product.image)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}