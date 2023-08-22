package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.HomeFragment
import com.example.myapplication.R
import com.example.myapplication.TalkFragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

//    private val HomeFragment = HomeFragment()
//    private val TalkFragment = TalkFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        var bnv_main = findViewById(R.id.bottomNavi) as BottomNavigationView
        if(MyApplication.checkAuth()) {

            bnv_main.run { setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.nav1 -> {
                        // 다른 프래그먼트 화면으로 이동하는 기능
                        val homeFragment = HomeFragment()
                        switchFragment(homeFragment)
                    }
                    R.id.nav2 -> {
                        val talkFragment = TalkFragment()
                        switchFragment(talkFragment)
                    }
//                R.id.nav3 -> {
//                    val settingFragment = SettingFragment()
//                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, settingFragment).commit()
//                }
                }
                true
            }
                selectedItemId = R.id.nav1
            }
        }else { // 로그인 안돼있으면 로그인 화면으로 이동
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }




    }
    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_layout, fragment)
        transaction.addToBackStack(null) // Optional: Add the fragment to the back stack
        transaction.commit()
    }

    override fun onStart() {
        super.onStart()


    }

//    private fun switchFragment(fragment: Fragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.main_layout, fragment)
//        transaction.addToBackStack(null) // Optional: Add the fragment to the back stack
//        transaction.commit()
//    }
}
