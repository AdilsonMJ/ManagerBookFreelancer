package com.example.managerbookfreelancer.main.ui.screens.professionalForm

import android.os.Bundle
import android.util.Patterns
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.databinding.FragmentFormNewProfessionalBinding
import com.example.managerbookfreelancer.main.core.entity.ClientEntity
import com.example.managerbookfreelancer.main.viewModel.FormNewProfessionalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FormNewProfessionalFragment : Fragment() {

    private var _binding: FragmentFormNewProfessionalBinding? = null
    private val binding get() = _binding!!
    private val args: FormNewProfessionalFragmentArgs by navArgs()
    private lateinit var viewModel: FormNewProfessionalViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FormNewProfessionalViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFormNewProfessionalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Show action bar
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar?.show()
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolBar()
        setDateOnFields()
        save()

    }

    private fun setupToolBar() {
        requireActivity().addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == android.R.id.home){
                    findNavController().navigateUp()
                    return true
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun save() {
        binding.BTNRecyclerViewNewProfessional.setOnClickListener {
            val name = binding.editTextName.requireText()
            val cellphone = binding.editTextCellphone.requireText()
            val email = binding.editTextEmail.requireText()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.FildEditTextEmail.requestFocus()
                binding.FildEditTextEmail.error
                Toast.makeText(requireContext(), "Email invalid", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val professional = ClientEntity(
                idClient = args.clientID,
                name = name,
                contact = cellphone,
                email = email
            )

            viewModel.insert(professional)

            findNavController().navigate(R.id.action_newProfessional_to_recyclerViewProfessionalFragment)

        }
    }

    private fun setDateOnFields() {
        if (args.clientID != 0L){
            viewLifecycleOwner.lifecycleScope.launch{
                val client = viewModel.getClientById(args.clientID)

                binding.editTextName.setText(client.name)
                binding.editTextCellphone.setText(client.contact)
                binding.editTextEmail.setText(client.email)

            }
        }
    }

    fun EditText.requireText(): String {
        val text = this.text.toString().trim()
        if (text.isEmpty()) {
            this.error = "Please fill this field"
            this.requestFocus()
        }

        return text
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        // hider action bar
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar?.hide()
        }

    }

}