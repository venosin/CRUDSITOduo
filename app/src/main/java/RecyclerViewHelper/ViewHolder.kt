package RecyclerViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import steven.flores.danielstevenfernandomerino.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    //En el viewHolder mando a llamar a los elementos de la card
    val txtNombreCard: TextView = view.findViewById(R.id.txtNombreCard)
    val imgDelete: ImageView = view.findViewById(R.id.imgDelete)
    val imgEdit: ImageView = view.findViewById(R.id.imgEdit)


}