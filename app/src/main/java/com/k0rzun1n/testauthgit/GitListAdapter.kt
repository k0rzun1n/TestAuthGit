package com.k0rzun1n.testauthgit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.k0rzun1n.testauthgit.data.ItemsItem
import com.squareup.picasso.Picasso


/**
 * Created by krz on 04-Apr-18.
 */
class GitListAdapter(dataSet: MutableList<ItemsItem?>, mContext: Context)
    : ArrayAdapter<ItemsItem>(mContext, R.layout.git_search_menu_item, dataSet)
        , View.OnClickListener {

    private var lastPosition = -1

    // View lookup cache
    private class ViewHolder {
        internal var ivPic: ImageView? = null
        internal var tvLgn: TextView? = null
    }

    override fun onClick(v: View) {
        Toast.makeText(v.context, "azaz", Toast.LENGTH_LONG).show()
//        add(ItemsItem(login = "KOKOKOKOK", avatarUrl = "http://i.imgur.com/DvpvklR.png"))
//
//        val position = v.getTag() as Int
//        val obj = getItem(position)
//        val dataModel = obj as ItemsItem

//        when (v.getId()) {
//            R.id.item_info -> Snackbar.make(v, "Release date " + dataModel!!.getFeature(), Snackbar.LENGTH_LONG)
//                    .setAction("No action", null).show()
//        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // Get the data item for this position
        val dataModel = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        val viewHolder: ViewHolder // view lookup cache stored in tag

        val result: View

        if (convertView == null) {

            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.git_search_menu_item, parent, false)
            viewHolder.ivPic = convertView!!.findViewById(R.id.ivProfilePic)
            viewHolder.tvLgn = convertView!!.findViewById(R.id.tvLogin)

            result = convertView

            convertView!!.setTag(viewHolder)
        } else {
            viewHolder = convertView!!.getTag() as ViewHolder
            result = convertView
        }

//        val animation = AnimationUtils.loadAnimation(mContext, if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top)
//        result.startAnimation(animation)
        lastPosition = position

//        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(viewHolder.ivPic!!)
//        Picasso.get().load("https://avatars1.githubusercontent.com/u/12001062?v=4").into(viewHolder.ivPic!!)
        Picasso.get().load(dataModel!!.avatarUrl).into(viewHolder.ivPic!!)
        viewHolder.tvLgn!!.setText(dataModel!!.login)
        // Return the completed view to render on screen
        return convertView
    }
}