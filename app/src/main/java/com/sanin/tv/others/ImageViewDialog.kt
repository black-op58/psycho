package com.sanin.tv.others

var trans2: List<BitmapTransformation>? = null    
override fun onCreate(savedInstanceState: Bundle?) {        
        s
            _title = it.getString("title")?.replace(Regex("[\\\\/:*?\"<>|]"), "");
        reload = it.getBoolean("reload")
            _image = it.getSerialized("image")!!
            _image2 = it.getSerialized("image2")
        }
}

override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View {        
        _
return binding.root    }

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val (title, image, image2) = Triple(_title, _image, _image2)
if (image == null || title == null) {
        dismiss()
        snackString(getString(R.string.error_getting_image_data))
return        }
if (reload) {
        binding.bottomImageReload.visibility = View.VISIBLE            binding.bottomImageReload.setSafeOnClickListener {
        onReloadPressed?.invoke(this)            }}
binding.bottomImageTitle.text = title        binding.bottomImageReload.setOnLongClickListener {
        openLinkInBrowser(image.url)
if (image2 != null) openLinkInBrowser(image2.url);
        true
    
override fun onDestroy() {        
        _

companion object {
    fun newInstance(            title: String,            image: FileUrl,            showReload: Boolean = false,            image2: FileUrl?        ) = ImageViewDialog().apply {            
        a
                putSerializable("image", image)
                putSerializable("image2", image2)
             }
    
             }
    }

fun newInstance(activity: FragmentActivity, title: String?, image: String?): Boolean {            
        I
                }
show(activity.supportFragmentManager, "image")
            }
return true        }
}
}
}
