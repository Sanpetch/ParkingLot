package com.example.parkinglot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkinglot.R
import com.example.parkinglot.ViewModel.ParkingLotViewModel
import com.example.parkinglot.ViewModel.UserViewModel
import com.example.parkinglot.databinding.FragmentParkinglotBinding
import com.example.parkinglot.model.ParkingLotsCheckOutRequest
import com.example.parkinglot.util.Resource
import com.example.parkinglot.util.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ParkingLotFragment : Fragment() {

    private var _binding: FragmentParkinglotBinding? = null
    private val binding get() = _binding!!
    private var licensePlate :String? = ""
    private var parkingNumber :String? = ""
    private var parking_id :String? = ""

    @Inject
    lateinit var sharedPrefManager : SharedPrefManager
    private val userViewModel: UserViewModel by viewModels()
    private val parkingLotViewModel: ParkingLotViewModel by viewModels()
    private val recycler: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentParkinglotBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.welcome.text = getString(R.string.welcome, sharedPrefManager.readUser())
        licensePlate = sharedPrefManager.readLicensePlate()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.checkout.setOnClickListener {

            val parkingLotsCheckOutRequest = ParkingLotsCheckOutRequest(parking_id!!,licensePlate!!, parkingNumber!!,"check_out",true);

            parkingLotViewModel.checkOut(parkingLotsCheckOutRequest)
        }
        parkingLotViewModel.getAllParking()

        observe();
    }

    override fun onResume() {
        super.onResume()
        licensePlate?.let { parkingLotViewModel.getSlotByLicensePlate(it) }

    }


    fun observe() {
        parkingLotViewModel.getParkingLots.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Success -> {
                    val adapter = ParkingLotAdapter(it.data,context)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }
                is Resource.Error -> {


                }
                is Resource.Loading ->{

                }
                else -> {}
            }
        }


        parkingLotViewModel.licenseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.parkinglot.text = getString(R.string.parking_slot_number,  it.data?.parking_no)
                    parkingNumber = it.data?.parking_no
                    parking_id = it.data?.id
                }
                is Resource.Error -> {


                }
                is Resource.Loading ->{

                }
                else -> {}
            }
        }


        parkingLotViewModel.checkOutData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.parkinglot.text = getString(R.string.parking_slot_number,  "-")
                    parkingLotViewModel.getAllParking()
                }
                is Resource.Error -> {


                }
                is Resource.Loading ->{

                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}