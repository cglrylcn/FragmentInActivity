package com.caglar.fragmentinactivity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.caglar.fragmentinactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    private var ticketState = 0
    private lateinit var binding: ActivityMainBinding

    var runnable : Runnable = Runnable {  }
    var handler = Handler(Looper.myLooper()!!)

    private var ticketState : MutableLiveData<Int> = MutableLiveData(0)

    //TicketFragment'tan nesne oluştur
    val ticketFragment = TicketFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //ilk açılışta anasayfada ticket fragment göster
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,ticketFragment).commit()


        binding.buttonSuccess.setOnClickListener {
            //geçiş yapılacak fragmanı belirtmek için geçiş yapılacak fragmenttan nesne oluşturur.
            val toSuccessFragment = SuccessFragment()
            //frameLayout'ta gösterilecek fragment'ı belirle
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout,toSuccessFragment).commit()

            //success geçtiğini kontrol etmek için ticketState=1 yapıyoruz
            ticketState.value = 1
        }

        binding.buttonFailed.setOnClickListener {
            //geçiş yapılacak fragmanı belirtmek için geçiş yapılacak fragmenttan nesne oluşturur.
            val toFailedFragment = FailedFragment()
            //frameLayout'ta gösterilecek fragment'ı belirle
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout,toFailedFragment).commit()

            //failed geçtiğini kontrol etmek için ticketState=2 yapıyoruz
            ticketState.value = 2
        }


        //ticketState'i observe ederek başka fragmenta geçip geçmediğini anlıyoruz
        ticketState.observe(this, Observer {
            if (ticketState.value != 0) {
                //4sn sonra olması için handler başlat
                ticketStateCheck()
            }
        })

        setContentView(binding.root)
    }

    private fun ticketStateCheck() {
        //ticketState kontrol edip ona göre UI güncelleyecek
        handler.postDelayed({
            // This method will be executed once the timer is over
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout,ticketFragment).commit()
        }, 4000)
    }
}