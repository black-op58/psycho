package com.sanin.tv.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.sanin.tv.databinding.BottomSheetCustomBinding
import kotlinx.coroutines.Job

class DiscordDialogFragment : DialogFragment() {

    private var _binding: BottomSheetCustomBinding? = null
    private val binding get() = _binding!!

    private var tokenRefreshJob: Job? = null
    private var mode: String = "nothing"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCustomBinding.inflate(inflater, container, false)
        return binding.root
    }

    
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mode = arguments?.getString("mode") ?: "nothing"
        setupButtons()
      }
    
      }
    private fun setupButtons() {
        if (mode == "nothing") {
        binding.previewButton1.visibility = View.GONE
            binding.previewButton2.visibility = View.GONE
        }
        
        }
        else {
            binding.previewButton1.visibility = View.VISIBLE
            binding.previewButton2.visibility = View.VISIBLE
            binding.previewButton1.text = when (mode) {
        "mal" -> "VIEW ON MYANIMELIST"
                else  -> "VIEW ON ANILIST"
            }
            
            }
            binding.previewButton2.text = when (mode) {
        "sanintv" -> "SANINTV PROFILE"
                else      -> "VIEW PROFILE"
            }
        
            }
        }
    }

    
    }

    override fun onDestroyView() {
        tokenRefreshJob?.cancel()
        _binding = null
        super.onDestroyView()
      }
    
      }
    override fun onDestroy() {
        tokenRefreshJob?.cancel()
        _binding = null
        super.onDestroy()
      }
    
      }
    companion object {
        fun newInstance(mode: String = "nothing"): DiscordDialogFragment {
            val f = DiscordDialogFragment()
            f.arguments = Bundle().apply {
        putString("mode", mode)
 }
            
 }
            return f
        }
    
        }
    }
}
