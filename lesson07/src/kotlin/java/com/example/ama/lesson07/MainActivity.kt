package com.example.ama.lesson07

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sv_circle.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.simple_animation))
        }
        sv_square.setOnClickListener {
            val set = AnimatorInflater.loadAnimator(this, R.animator.rotation_animator) as AnimatorSet
            set.setTarget(it)
            set.start()
        }
        sv_triangle.setOnClickListener {
            val set = AnimatorInflater.loadAnimator(this, R.animator.jump_animation) as AnimatorSet
            set.setTarget(it)
            set.start()
        }
    }
}