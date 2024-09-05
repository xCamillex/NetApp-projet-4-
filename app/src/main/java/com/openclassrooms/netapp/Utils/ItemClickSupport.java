package com.openclassrooms.netapp.Utils;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Cette classe facilite la gestion des événements de clic et de clic long dans un RecyclerView.
 * <p>
 * Elle fournit un mécanisme simple pour détecter les clics et les clics longs sur les éléments d'une RecyclerView
 * sans avoir à définir des écouteurs de clics directement dans le ViewHolder ou l'adaptateur.
 * </p>
 */

public class ItemClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private int mItemID;

    // Écouteur pour les clics sur les éléments de la RecyclerView
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    // Écouteur pour les clics longs sur les éléments de la RecyclerView
    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    // Écouteur pour les changements d'état des vues enfants du RecyclerView
    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    /**
     * Constructeur privé pour initialiser ItemClickSupport avec le RecyclerView et un identifiant unique.
     * <p>
     * Ce constructeur est utilisé uniquement par les méthodes statiques {@link #addTo} et {@link #removeFrom}.
     * </p>
     *
     * @param recyclerView Le RecyclerView auquel l'écouteur de clic est attaché.
     * @param itemID Un identifiant unique pour cet ItemClickSupport.
     */
    private ItemClickSupport(RecyclerView recyclerView, int itemID) {
        mRecyclerView = recyclerView;
        mItemID = itemID;
        mRecyclerView.setTag(itemID, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    /**
     * Ajoute un ItemClickSupport au RecyclerView pour gérer les clics et clics longs sur les éléments.
     * <p>
     * Si un ItemClickSupport avec le même itemID existe déjà, il est réutilisé.
     * </p>
     *
     * @param view Le RecyclerView auquel ajouter l'ItemClickSupport.
     * @param itemID L'identifiant unique pour l'ItemClickSupport.
     * @return L'instance de ItemClickSupport attachée au RecyclerView.
     */
    public static ItemClickSupport addTo(RecyclerView view, int itemID) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(itemID);
        if (support == null) {
            support = new ItemClickSupport(view, itemID);
        }
        return support;
    }

    /**
     * Supprime un ItemClickSupport du RecyclerView.
     * <p>
     * Le ItemClickSupport est détaché et l'identifiant unique est supprimé.
     * </p>
     *
     * @param view Le RecyclerView duquel retirer l'ItemClickSupport.
     * @param itemID L'identifiant unique pour l'ItemClickSupport.
     * @return L'instance de ItemClickSupport qui a été retirée, ou null si aucune instance n'a été trouvée.
     */
    public static ItemClickSupport removeFrom(RecyclerView view, int itemID) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(itemID);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    /**
     * Définit un écouteur pour les clics sur les éléments de la RecyclerView.
     *
     * @param listener L'écouteur à définir pour les clics.
     * @return L'instance actuelle de ItemClickSupport.
     */
    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    /**
     * Définit un écouteur pour les clics longs sur les éléments de la RecyclerView.
     *
     * @param listener L'écouteur à définir pour les clics longs.
     * @return L'instance actuelle de ItemClickSupport.
     */
    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    /**
     * Détache l'ItemClickSupport du RecyclerView et supprime son identifiant.
     *
     * @param view Le RecyclerView duquel détacher l'ItemClickSupport.
     */
    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(mItemID, null);
    }

    /**
     * Interface pour écouter les événements de clic sur les éléments de la RecyclerView.
     */
    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    /**
     * Interface pour écouter les événements de clic long sur les éléments de la RecyclerView.
     */
    public interface OnItemLongClickListener {

        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}