@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.kaufmannmarek.library

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

open class CustomFirebase(private val context: Context) {
    private var firestore: FirebaseFirestore? = null

    /**
     * @return instance of FirebaseFirestore
     */
    fun getFirestore(): FirebaseFirestore {
        if (this.firestore == null)
            this.firestore = FirebaseFirestore.getInstance()
        return this.firestore!!
    }

    /**
     * @return context of the default activity
     */
    internal fun getContext(): Context {
        return this.context
    }

    /**
     * @return collection reference under provided name
     *
     * @param collectionName is name of the collection
     */
    fun getCollectionReference(collectionName: String): CollectionReference {
        return getFirestore().collection(collectionName)
    }

    /**
     * @return collection reference under provided name
     *
     * @param collectionName is the int reference to String, which holds name of the collection
     */
    fun getCollectionReference(collectionName: Int): CollectionReference {
        return getCollectionReference(getContext().getString(collectionName))
    }

    /**
     * @return task, which will try to retrieve collection under provided name
     *
     * @param collectionName is the name of the collection
     */
    fun getCollection(collectionName: String): Task<QuerySnapshot> {
        return getCollectionReference(collectionName).get()
    }

    /**
     * @return task, which will try to retrieve collection under provided name
     *
     * @param collectionName is the int reference to String, which holds name of the collection
     */
    fun getCollection(collectionName: Int): Task<QuerySnapshot> {
        return getCollection(getContext().getString(collectionName))
    }

    /**
     * @return document reference in the specific collection
     *
     * @param collectionName is the name of the collection, where the document is located
     * @param documentName is the name of the document
     */
    fun getDocumentReference(collectionName: String, documentName: String): DocumentReference {
        return getCollectionReference(collectionName).document(documentName)
    }

    /**
     * @return document reference in the specific collection
     *
     * @param collectionName is the int reference to String, which holds name of the collection, where the document is located
     * @param documentName is the int reference to String, which holds name of the document
     */
    fun getDocumentReference(collectionName: Int, documentName: Int): DocumentReference {
        return getDocumentReference(
            getContext().getString(collectionName),
            getContext().getString(documentName)
        )
    }

    /**
     * @return document reference in the specific collection
     *
     * @param collectionName is the int reference to String, which holds name of the collection, where the document is located
     * @param documentName is the name of the document
     */
    fun getDocumentReference(collectionName: Int, documentName: String): DocumentReference {
        return getDocumentReference(getContext().getString(collectionName), documentName)
    }

    /**
     * @return document reference in the specific collection
     *
     * @param collectionName is the name of the collection, where the document is located
     * @param documentName is the int reference to String, which holds name of the document
     */
    fun getDocumentReference(collectionName: String, documentName: Int): DocumentReference {
        return getDocumentReference(collectionName, getContext().getString(documentName))
    }

    /**
     * @return uid of the currently logged in user
     */
    fun getUserUid(): String {
        return FirebaseAuth.getInstance().uid!!
    }

    /**
     * @return document reference in collection, which name is currently logged in user uid
     *
     * @param documentName is the name of the document
     */
    fun getUserDocumentReference(documentName: String): DocumentReference {
        return getDocumentReference(getUserUid(), documentName)
    }

    /**
     * @return document reference in collection, which name is currently logged in user uid
     *
     * @param documentName is the int reference to String, which holds name of the document
     */
    fun getUserDocumentReference(documentName: Int): DocumentReference {
        return getUserDocumentReference(getContext().getString(documentName))
    }

    /**
     * @return task, which will try to retrieve document in specific collection
     *
     * @param collectionName is the name of the collection, where the document is stored
     * @param documentName is the name of document, which should be retrieved
     */
    fun getDocument(collectionName: String, documentName: String): Task<DocumentSnapshot> {
        return getDocumentReference(collectionName, documentName).get()
    }

    /**
     * @return task, which will try to retrieve document in specific collection
     *
     * @param collectionName is the int reference to String, which holds name of the collection, where the document is stored
     * @param documentName is the int reference to String, which holds name of document, which should be retrieved
     */
    fun getDocument(collectionName: Int, documentName: Int): Task<DocumentSnapshot> {
        return getDocument(
            getContext().getString(collectionName),
            getContext().getString(documentName)
        )
    }

    /**
     * @return task, which will try to retrieve document in specific collection
     *
     * @param collectionName is the int reference to String, which holds name of the collection, where the document is stored
     * @param documentName is the name of document, which should be retrieved
     */
    fun getDocument(collectionName: Int, documentName: String): Task<DocumentSnapshot> {
        return getDocument(getContext().getString(collectionName), documentName)
    }

    /**
     * @return task, which will try to retrieve document in specific collection
     *
     * @param collectionName is the name of the collection, where the document is stored
     * @param documentName is the int reference to String, which holds name of document, which should be retrieved
     */
    fun getDocument(collectionName: String, documentName: Int): Task<DocumentSnapshot> {
        return getDocument(collectionName, getContext().getString(documentName))
    }

    /**
     * @return task, which will try to retrieve document in collection, which name is currently logged in user uid
     *
     * @param documentName is the name of the document
     */
    fun getUserDocument(documentName: String): Task<DocumentSnapshot> {
        return getDocument(getUserUid(), documentName)
    }

    /**
     * @return task, which will try to retrieve document in collection, which name is currently logged in user uid
     *
     * @param documentName is the int reference to String, which holds name of the document
     */
    fun getUserDocument(documentName: Int): Task<DocumentSnapshot> {
        return getUserDocument(getContext().getString(documentName))
    }


    class Batch(context: Context) : CustomFirebase(context) {
        private var batch: WriteBatch? = null

        /**
         * @return committed batch
         */
        fun commit(): Task<Void> {
            return getBatch().commit()
        }

        /**
         * @return batch
         */
        fun getBatch(): WriteBatch {
            if (this.batch == null)
                this.batch = getFirestore().batch()
            return this.batch!!
        }

        /**
         * Removes provided value from provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the name of the document, where the array with the value is stored
         * @param arrayName is the name of the array, where the value is stored
         * @param value is the value to be removed
         */
        fun removeElementFromArray(documentName: String, arrayName: String, value: Any) {
            getBatch().update(
                getUserDocumentReference(documentName),
                arrayName,
                FieldValue.arrayRemove(value)
            )
        }

        /**
         * Removes provided value from provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the int reference to String, which holds name of the document, where the array with the value is stored
         * @param arrayName is the int reference to String, which holds name of the array, where the value is stored
         * @param value is the value to be removed
         */
        fun removeElementFromArray(documentName: Int, arrayName: Int, value: Any) {
            removeElementFromArray(
                getContext().getString(documentName),
                getContext().getString(arrayName),
                value
            )
        }

        /**
         * Removes provided value from provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the int reference to String, which holds name of the document, where the array with the value is stored
         * @param arrayName is the name of the array, where the value is stored
         * @param value is the value to be removed
         */
        fun removeElementFromArray(documentName: Int, arrayName: String, value: Any) {
            removeElementFromArray(getContext().getString(documentName), arrayName, value)
        }

        /**
         * Removes provided value from provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the name of the document, where the array with the value is stored
         * @param arrayName is the name of the array, where the value is stored
         * @param value is the value to be removed
         */
        fun removeElementFromArray(documentName: String, arrayName: Int, value: Any) {
            removeElementFromArray(documentName, getContext().getString(arrayName), value)
        }

        /**
         * Adds provided value to provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the name of the document, where the array with the value is stored
         * @param arrayName is the name of the array, where the value is stored
         * @param value is the value to be added
         */
        fun addElementInArray(documentName: String, arrayName: String, value: Any) {
            getBatch().update(getUserDocumentReference(documentName), arrayName, value)
        }

        /**
         * Adds provided value to provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the name of the document, where the array with the value is stored
         * @param arrayName is the int reference to String, which holds name of the array, where the value is stored
         * @param value is the value to be added
         */
        fun addElementInArray(documentName: Int, arrayName: Int, value: Any) {
            addElementInArray(
                getContext().getString(documentName),
                getContext().getString(arrayName),
                value
            )
        }

        /**
         * Adds provided value to provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the int reference to String, which holds name of the document, where the array with the value is stored
         * @param arrayName is the name of the array, where the value is stored
         * @param value is the value to be added
         */
        fun addElementInArray(documentName: Int, arrayName: String, value: Any) {
            addElementInArray(getContext().getString(documentName), arrayName, value)
        }

        /**
         * Adds provided value to provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the name of the document, where the array with the value is stored
         * @param arrayName is the int reference to String, which holds name of the array, where the value is stored
         * @param value is the value to be added
         */
        fun addElementInArray(documentName: String, arrayName: Int, value: Any) {
            addElementInArray(documentName, getContext().getString(arrayName), value)
        }

        /**
         * Update provided value to provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the name of the document, where the array with the value is stored
         * @param arrayName is the name of the array, where the value is stored
         * @param oldValue is the value, which will be replaced
         * @param newValue is the value, which will replace oldValue
         */
        fun updateElementInArray(
            documentName: String,
            arrayName: String,
            oldValue: Any,
            newValue: Any
        ) {
            addElementInArray(documentName, arrayName, newValue)
            removeElementFromArray(documentName, arrayName, oldValue)
        }

        /**
         * Update provided value to provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the int reference to String, which holds name of the document, where the array with the value is stored
         * @param arrayName is the int reference to String, which holds name of the array, where the value is stored
         * @param oldValue is the value, which will be replaced
         * @param newValue is the value, which will replace oldValue
         */
        fun updateElementInArray(documentName: Int, arrayName: Int, oldValue: Any, newValue: Any) {
            updateElementInArray(
                getContext().getString(documentName),
                getContext().getString(arrayName),
                oldValue,
                newValue
            )
        }

        /**
         * Update provided value to provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the int reference to String, which holds name of the document, where the array with the value is stored
         * @param arrayName is the name of the array, where the value is stored
         * @param oldValue is the value, which will be replaced
         * @param newValue is the value, which will replace oldValue
         */
        fun updateElementInArray(
            documentName: Int,
            arrayName: String,
            oldValue: Any,
            newValue: Any
        ) {
            updateElementInArray(
                getContext().getString(documentName),
                arrayName,
                oldValue,
                newValue
            )
        }

        /**
         * Update provided value to provided array in provided document in the collection, which name is uid of the currently logged user
         *
         * @param documentName is the name of the document, where the array with the value is stored
         * @param arrayName is the int reference to String, which holds name of the array, where the value is stored
         * @param oldValue is the value, which will be replaced
         * @param newValue is the value, which will replace oldValue
         */
        fun updateElementInArray(
            documentName: String,
            arrayName: Int,
            oldValue: Any,
            newValue: Any
        ) {
            updateElementInArray(
                documentName,
                getContext().getString(arrayName),
                oldValue,
                newValue
            )
        }

    }
}