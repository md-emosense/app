package com.example.emosense.adapter


import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.emosense.R
import com.example.emosense.data.response.SpeechItem
import com.example.emosense.databinding.ItemFlashcardBinding
import java.io.IOException

class ListFlashcardAdapter : ListAdapter<SpeechItem, ListFlashcardAdapter.ViewHolder>(DIFF_CALLBACK) {
    private var listener: ((SpeechItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (SpeechItem) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFlashcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            listener?.invoke(user)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(private val binding: ItemFlashcardBinding) : RecyclerView.ViewHolder(binding.root) {
        private var mediaPlayer: MediaPlayer? = null
        private var isPlaying = false

        private val context: Context = binding.root.context
        private val label = binding.label
        private val flipButton = binding.btnFlip
        private val playButton = binding.playAudio
        private val pauseButton = binding.pauseAudio

        fun bind(speech: SpeechItem) {
            label.text = speech.word

            flipButton.setOnClickListener {
                flipCard(binding.card1, R.id.cardFront1, R.id.cardBack1)
            }

            playButton.setOnClickListener {
                playAudio(speech.urlAudio, speech.word)
            }

            pauseButton.setOnClickListener {
                pauseAudio()
            }
        }

        private fun playAudio(audioUrl: String, type: String) {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    setOnCompletionListener {
                        this@ViewHolder.isPlaying = false
                        updatePlayPauseButtonVisibility()
                    }
                }
            } else {
                mediaPlayer?.reset()
            }

            try {
                mediaPlayer?.setDataSource(audioUrl)
                mediaPlayer?.prepare()
                mediaPlayer?.start()
                isPlaying = true
                updatePlayPauseButtonVisibility()
                Toast.makeText(context, "Kamu sedang mendengar audio $type", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        private fun pauseAudio() {
            mediaPlayer?.pause()
            isPlaying = false
            updatePlayPauseButtonVisibility()
        }

        private fun updatePlayPauseButtonVisibility() {
            if (isPlaying) {
                playButton.visibility = View.GONE
                pauseButton.visibility = View.VISIBLE
            } else {
                playButton.visibility = View.VISIBLE
                pauseButton.visibility = View.GONE
            }
        }

        private fun flipCard(card: View, frontId: Int, backId: Int) {
            val backVisibility = if (card.tag == "front") View.VISIBLE else View.GONE
            val frontVisibility = if (card.tag == "back") View.VISIBLE else View.GONE

            val flipInAnimator = if (card.tag == "front") R.animator.flip_to_back else R.animator.flip_to_front
            val flipOutAnimator = if (card.tag == "back") R.animator.flip_to_front else R.animator.flip_to_back

            val animFlipIn = AnimatorInflater.loadAnimator(context, flipInAnimator)
            animFlipIn.setTarget(card)

            animFlipIn.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    card.findViewById<View>(frontId).visibility = frontVisibility
                    card.findViewById<View>(backId).visibility = backVisibility

                    card.tag = if (card.tag == "front") "back" else "front"
                }
            })

            val animFlipOut = AnimatorInflater.loadAnimator(context, flipOutAnimator)
            animFlipOut.setTarget(card)

            animFlipIn.start()
            animFlipOut.start()
        }

        init {
            mediaPlayer?.setOnCompletionListener {
                isPlaying = false
                updatePlayPauseButtonVisibility()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SpeechItem>() {
            override fun areItemsTheSame(oldItem: SpeechItem, newItem: SpeechItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SpeechItem, newItem: SpeechItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

