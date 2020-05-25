@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.kaufmannmarek.library

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

/**
 * Creates object, which allow operations with firestore collections.
 *
 * @param context of currently displayed activity
 */
open class FirestoreCollection(private val context: Context) {
    private var firestore: FirebaseFirestore? = null
    private var collectionName: String? = null

    /**
     * Creates object, which allow operations with firestore collections.
     *
     * @param context of currently displayed activity
     * @param collectionName is name of collection
     */
    constructor(context: Context, collectionName: String) : this(context) {
        setCollectionName(collectionName)
    }

    /**
     * Creates object, which allow operations with firestore collections.
     *
     * @param context of currently displayed activity
     * @param collectionName is int reference to String, which holds name of collection
     */
    constructor(context: Context, collectionName: Int) : this(
        context,
        context.getString(collectionName)
    )

    init {
        if (this.collectionName == null)
            setCollectionName(getActiveUid())
    }

    /**
     * @return instance of the firebase firestore
     */
    private fun getFirestoreInstance(): FirebaseFirestore {
        if (this.firestore == null)
            this.firestore = FirebaseFirestore.getInstance()
        return this.firestore!!
    }

    /**
     * @return context of currently displayed activity
     */
    internal fun getContext(): Context {
        return this.context
    }

    /**
     * Will set collection name
     *
     * @param collectionName is name of the collection
     */
    internal fun setCollectionName(collectionName: String) {
        this.collectionName = collectionName
    }

    /**
     * @return name of the collection
     */
    private fun getName(): String {
        return this.collectionName!!
    }

    /**
     * @return uid of the currently logged user
     */
    internal fun getActiveUid(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    /**
     * @return reference of the collection
     */
    fun getReference(): CollectionReference {
        return getFirestoreInstance().collection(getName())
    }

    /**
     * @return if successful, it will return the collection
     */
    fun get(): Task<*> {
        return getReference().get()
    }
}

/**
 * Create object, which allows operations with firestore documents in collection, which name is uid of currently logged user
 *
 * @param context of currently displayed activity
 * @param documentName is the name of the document, where the operations will be performed
 */
class FirestoreDocument(context: Context, private val documentName: String) :
    FirestoreCollection(context) {

    /**
     * Create object, which allows operations with firestore documents
     *
     * @param context of currently displayed activity
     * @param collectionName is the name of the collection, where the document is stored
     * @param documentName is the name of the document, where the operations will be performed
     */
    constructor(context: Context, collectionName: String, documentName: String) : this(
        context,
        documentName
    ) {
        setCollectionName(collectionName)
    }

    /**
     * Create object, which allows operations with firestore documents
     *
     * @param context of currently displayed activity
     * @param collectionName is the name of the collection, where the document is stored
     * @param documentName is the name of the document, where the operations will be performed
     */
    constructor(context: Context, collectionName: String, documentName: Int) : this(
        context,
        collectionName,
        context.getString(documentName)
    )

    /**
     * Create object, which allows operations with firestore documents
     *
     * @param context of currently displayed activity
     * @param collectionName is int reference to String, which holds name of the collection, where the document is stored
     * @param documentName is the name of the document, where the operations will be performed
     */
    constructor(context: Context, collectionName: Int, documentName: String) : this(
        context,
        context.getString(collectionName),
        documentName
    )

    /**
     * Create object, which allows operations with firestore documents
     *
     * @param context of currently displayed activity
     * @param collectionName is int reference to String, which holds name of the collection, where the document is stored
     * @param documentName is int reference to String, which holds name of the document, where the operations will be performed
     */
    constructor(context: Context, collectionName: Int, documentName: Int) : this(
        context,
        context.getString(collectionName),
        context.getString(documentName)
    )

    /**
     * Create object, which allows operations with firestore documents in collection, which name is uid of currently logged user
     *
     * @param context of currently displayed activity
     * @param documentName is the name of the document, where the operations will be performed
     */
    constructor(context: Context, documentName: Int) : this(
        context,
        context.getString(documentName)
    )

    /**
     * @return provided name of the document
     */
    private fun getDocumentName(): String {
        return this.documentName
    }

    /**
     * @return document reference
     */
    fun getDocumentReference(): DocumentReference {
        return getReference().document(getDocumentName())
    }

    /**
     * @return if the task is successful, retrieved document
     */
    fun getDocument(): Task<*> {
        return getDocumentReference().get()
    }

    /**
     * @return if the task is successful, it will delete document
     */
    fun deleteDocument(): Task<*> {
        return getDocumentReference().delete()
    }

    /**
     * @return if the task is successful, it will update document
     * @param documentData are data, which will be put in document
     */
    fun updateDocument(documentData: HashMap<String, Any>): Task<*> {
        return getDocumentReference().update(documentData)
    }

    /**
     * @return if the task is successful, it will add value in array
     * @param value is value to be added
     * @param arrayName is name of the array, where the value should be added
     */
    fun addValue(value: Any, arrayName: String): Task<*> {
        return getDocumentReference().update(arrayName, FieldValue.arrayUnion(value))
    }

    /**
     * @return if the task is successful, it will add a value in array
     * @param value is value to be added
     * @param arrayName is int reference to String, which holds name of the array, where the value should be added
     */
    fun addValue(value: Any, arrayName: Int): Task<*> {
        return addValue(value, getContext().getString(arrayName))
    }

    /**
     * @return if the task is successful, it will remove a value in array
     * @param value is value to be added
     * @param arrayName is name of the array, where the value should be added
     */
    fun removeValue(value: Any, arrayName: String): Task<*> {
        return getDocumentReference().update(arrayName, FieldValue.arrayRemove(value))
    }

    /**
     * @return if the task is successful, it will add value in array
     * @param value is value to be added
     * @param arrayName is int reference to String, which holds name of the array, where the value should be added
     */
    fun removeValue(value: Any, arrayName: Int): Task<*> {
        return removeValue(value, getContext().getString(arrayName))
    }

    /**
     * @return if the task is successful, it will increase value in document
     * @param paramName is name of the variable, which should be increased
     * @param howMuch is value, how much the current value should be increased
     */
    fun increaseValue(paramName: String, howMuch: Int): Task<*> {
        return getDocumentReference().update(paramName, FieldValue.increment(howMuch.toLong()))
    }

    /**
     * @return if the task is successful, it will increase value in document
     * @param paramName is int reference to String, which holds name of the variable, which should be increased
     * @param howMuch is value, how much the current value should be increased
     */
    fun increaseValue(paramName: Int, howMuch: Int): Task<*> {
        return increaseValue(getContext().getString(paramName), howMuch)
    }

    /**
     * @return if the task is successful, it will increase value by one in document
     * @param paramName is name of the variable, which should be increased
     */
    fun increaseValue(paramName: String): Task<*> {
        return increaseValue(paramName, 1)
    }

    /**
     * @return if the task is successful, it will increase value by one in document
     * @param paramName is int reference to String, which holds name of the variable, which should be increased
     */
    fun increaseValue(paramName: Int): Task<*> {
        return increaseValue(getContext().getString(paramName))
    }

    /**
     * @return if the task is successful, it will decrease value in document
     * @param paramName is name of the variable, which should be increased
     */
    fun decreaseValue(paramName: String, howMuch: Int): Task<*> {
        return getDocumentReference().update(paramName, FieldValue.increment(-howMuch.toLong()))
    }

    /**
     * @return if the task is successful, it will decrease value in document
     * @param paramName is int reference to String, which holds name of the variable, which should be increased
     */
    fun decreaseValue(paramName: Int, howMuch: Int): Task<*> {
        return decreaseValue(getContext().getString(paramName), howMuch)
    }

    /**
     * @return if the task is successful, it will decrease value by one in document
     * @param paramName is name of the variable, which should be increased
     */
    fun decreaseValue(paramName: String): Task<*> {
        return decreaseValue(paramName, 1)
    }

    /**
     * @return if the task is successful, it will decrease value by one in document
     * @param paramName is int reference to String, which holds name of the variable, which should be increased
     */
    fun decreaseValue(paramName: Int): Task<*> {
        return decreaseValue(getContext().getString(paramName))
    }
}

/**
 * Creates batch object, which can perform more actions at once. Once you add all required operations, call commit.
 *
 * @param context of current activity
 */
class FireStoreBatch(private val context: Context) {
    private var batch: WriteBatch? = null

    /**
     * @return batch reference
     */
    fun getBatch(): WriteBatch {
        if (this.batch == null)
            this.batch = FirebaseFirestore.getInstance().batch()
        return this.batch!!
    }

    /**
     * @return committed batch
     */
    fun commit(): Task<Void> {
        return getBatch().commit()
    }

    /**
     * Adds update document operation to the batch queue
     *
     * @param firestoreDocument is object, which defines collection and document, which should be updated
     * @param newData are data, which will be putted into the document. All existing data in the document will be erased by this operation
     */
    fun updateDocument(firestoreDocument: FirestoreDocument, newData: HashMap<String, Any>) {
        getBatch().set(firestoreDocument.getDocumentReference(), newData)
    }

    /**
     * Adds delete document operation to the batch queue
     *
     * @param firestoreDocument is object, which defines collection and document, which should be deleted
     */
    fun deleteDocument(firestoreDocument: FirestoreDocument) {
        getBatch().delete(firestoreDocument.getDocumentReference())
    }

    /**
     * Adds remove value from array in a document operation to the batch
     *
     * @param firestoreDocument is object, which defines document in collection, where the value currently is
     * @param arrayName is name of the array, where the value currently is
     * @param value is the value, which should be removed
     */
    fun removeElementFromDocumentArray(
        firestoreDocument: FirestoreDocument,
        arrayName: String,
        value: Any
    ) {
        getBatch().update(
            firestoreDocument.getDocumentReference(),
            arrayName,
            FieldValue.arrayRemove(value)
        )
    }

    /**
     * Adds remove value from array in a document operation to the batch
     *
     * @param firestoreDocument is object, which defines document in collection, where the value currently is
     * @param arrayName is int, which is reference to String, which holds name of the array, where the value currently is
     * @param value is the value, which should be removed
     */
    fun removeElementFromDocumentArray(
        firestoreDocument: FirestoreDocument,
        arrayName: Int,
        value: Any
    ) {
        getBatch().update(
            firestoreDocument.getDocumentReference(),
            this.context.getString(arrayName),
            FieldValue.arrayRemove(value)
        )
    }

    /**
     * Adds add value in array in a document operation to the batch.
     *
     * @param firestoreDocument is object, which defines document in collection, where the value currently is
     * @param arrayName is int, which is reference to String, which holds name of the array, where the value currently is
     * @param value is the value, which should be removed
     */
    fun moveValueToAnotherDocument(
        firestoreDocument: FirestoreDocument,
        arrayName: String,
        value: Any
    ) {
        getBatch().update(
            firestoreDocument.getDocumentReference(),
            arrayName,
            FieldValue.arrayUnion(value)
        )
    }

    /**
     * Adds add value in array in a document operation to the batch. If the value doe
     *
     * @param firestoreDocument is object, which defines document in collection, where the value currently is
     * @param arrayName is int, which is reference to String, which holds name of the array, where the value currently is
     * @param value is the value, which should be removed
     */
    fun moveValueToAnotherDocument(
        firestoreDocument: FirestoreDocument,
        arrayName: Int,
        value: Any
    ) {
        moveValueToAnotherDocument(firestoreDocument, this.context.getString(arrayName), value)
    }

    /**
     * Adds add value in array in a document operation and remove value in different document operation to the batch.
     *
     * @param destinationFirestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param destinationArray is the name of the array, where the value should placed
     * @param value is the value, which will be placed in the destinationArray
     * @param defaultFirestoreDocument is object, which defines document in collection, where is a defaultArray, from where a defaultValue should be removed
     * @param defaultArray is the name of the array, from where the defaultValue should removed
     * @param defaultValue is the value, which will be removed from the defaultArray
     */
    fun moveAndUpdateValueInDocuments(
        destinationFirestoreDocument: FirestoreDocument,
        destinationArray: String,
        value: Any,
        defaultFirestoreDocument: FirestoreDocument,
        defaultArray: String,
        defaultValue: Any
    ) {
        removeElementFromDocumentArray(defaultFirestoreDocument, defaultArray, defaultValue)
        moveValueToAnotherDocument(destinationFirestoreDocument, destinationArray, value)
    }

    /**
     * Adds add value in array in a document operation and remove value in different document operation to the batch.
     *
     * @param destinationFirestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param destinationArray is the reference to String, which holds name of the array, where the value should placed
     * @param value is the value, which will be placed in the destinationArray
     * @param defaultFirestoreDocument is object, which defines document in collection, where is a defaultArray, from where a defaultValue should be removed
     * @param defaultArray is the name of the array, from where the defaultValue should removed
     * @param defaultValue is the value, which will be removed from the defaultArray
     */
    fun moveAndUpdateValueInDocuments(
        destinationFirestoreDocument: FirestoreDocument,
        destinationArray: Int,
        value: Any,
        defaultFirestoreDocument: FirestoreDocument,
        defaultArray: String,
        defaultValue: Any
    ) {
        moveAndUpdateValueInDocuments(
            destinationFirestoreDocument,
            this.context.getString(destinationArray),
            value,
            defaultFirestoreDocument,
            defaultArray,
            defaultValue
        )
    }

    /**
     * Adds add value in array in a document operation and remove value in different document operation to the batch.
     *
     * @param destinationFirestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param destinationArray is the name of the array, where the value should placed
     * @param value is the value, which will be placed in the destinationArray
     * @param defaultFirestoreDocument is object, which defines document in collection, where is a defaultArray, from where a defaultValue should be removed
     * @param defaultArray is the reference to String, which holds the name of the array, from where the defaultValue should removed
     * @param defaultValue is the value, which will be removed from the defaultArray
     */
    fun moveAndUpdateValueInDocuments(
        destinationFirestoreDocument: FirestoreDocument,
        destinationArray: String,
        value: Any,
        defaultFirestoreDocument: FirestoreDocument,
        defaultArray: Int,
        defaultValue: Any
    ) {
        moveAndUpdateValueInDocuments(
            destinationFirestoreDocument,
            destinationArray,
            value,
            defaultFirestoreDocument,
            this.context.getString(defaultArray),
            defaultValue
        )
    }

    /**
     * Adds add value in array in a document operation and remove value in different document operation to the batch.
     *
     * @param destinationFirestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param destinationArray is the reference to String, which holds name of the array, where the value should placed
     * @param value is the value, which will be placed in the destinationArray
     * @param defaultFirestoreDocument is object, which defines document in collection, where is a defaultArray, from where a defaultValue should be removed
     * @param defaultArray is the reference to String, which holds the name of the array, from where the defaultValue should removed
     * @param defaultValue is the value, which will be removed from the defaultArray
     */
    fun moveAndUpdateValueInDocuments(
        destinationFirestoreDocument: FirestoreDocument,
        destinationArray: Int,
        value: Any,
        defaultFirestoreDocument: FirestoreDocument,
        defaultArray: Int,
        defaultValue: Any
    ) {
        moveAndUpdateValueInDocuments(
            destinationFirestoreDocument,
            this.context.getString(destinationArray),
            value,
            defaultFirestoreDocument,
            this.context.getString(defaultArray),
            defaultValue
        )
    }

    /**
     * Adds add value in one array in and remove value in another array in a same document operation to the batch.
     *
     * @param firestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param destinationArray is the name of the array, where the value should placed
     * @param value is the value, which will be placed in the destinationArray
     * @param defaultArray is the name of the array, from where the defaultValue should removed
     * @param defaultValue is the value, which will be removed from the defaultArray
     */
    fun moveAndUpdateValueInDocument(
        firestoreDocument: FirestoreDocument,
        destinationArray: String,
        value: Any,
        defaultArray: String,
        defaultValue: Any
    ) {
        moveAndUpdateValueInDocuments(
            firestoreDocument,
            destinationArray,
            value,
            firestoreDocument,
            defaultArray,
            defaultValue
        )
    }

    /**
     * Adds add value in one array in and remove value in another array in a same document operation to the batch.
     *
     * @param firestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param destinationArray is reference to String, which holds name of the array, where the value should placed
     * @param value is the value, which will be placed in the destinationArray
     * @param defaultArray is the name of the array, from where the defaultValue should removed
     * @param defaultValue is the value, which will be removed from the defaultArray
     */
    fun moveAndUpdateValueInDocument(
        firestoreDocument: FirestoreDocument,
        destinationArray: Int,
        value: Any,
        defaultArray: String,
        defaultValue: Any
    ) {
        moveAndUpdateValueInDocument(
            firestoreDocument,
            this.context.getString(destinationArray),
            value,
            defaultArray,
            defaultValue
        )
    }

    /**
     * Adds add value in one array in and remove value in another array in a same document operation to the batch.
     *
     * @param firestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param destinationArray is the name of the array, where the value should placed
     * @param value is the value, which will be placed in the destinationArray
     * @param defaultArray is the reference to String, which holds the name of the array, from where the defaultValue should removed
     * @param defaultValue is the value, which will be removed from the defaultArray
     */
    fun moveAndUpdateValueInDocument(
        firestoreDocument: FirestoreDocument,
        destinationArray: String,
        value: Any,
        defaultArray: Int,
        defaultValue: Any
    ) {
        moveAndUpdateValueInDocument(
            firestoreDocument,
            destinationArray,
            value,
            this.context.getString(defaultArray),
            defaultValue
        )
    }

    /**
     * Adds add value in one array in and remove value in another array in a same document operation to the batch.
     *
     * @param firestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param destinationArray is the reference to String, which holds name of the array, where the value should placed
     * @param value is the value, which will be placed in the destinationArray
     * @param defaultArray is the reference to String, which holds the name of the array, from where the defaultValue should removed
     * @param defaultValue is the value, which will be removed from the defaultArray
     */
    fun moveAndUpdateValueInDocument(
        firestoreDocument: FirestoreDocument,
        destinationArray: Int,
        value: Any,
        defaultArray: Int,
        defaultValue: Any
    ) {
        moveAndUpdateValueInDocument(
            firestoreDocument,
            this.context.getString(destinationArray),
            value,
            this.context.getString(defaultArray),
            defaultValue
        )
    }

    /**
     * Moves a value from document to another document with same array name
     *
     * @param destinationFirestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param arrayName is name of the array, where the value is currently placed
     * @param value is the value, which will be placed in the destinationArray
     * @param defaultDocumentReference is object, which defines document in collection, where is a defaultArray, from where a defaultValue should be removed
     */
    fun moveValueToAnotherDocument(
        destinationFirestoreDocument: FirestoreDocument,
        arrayName: String,
        value: Any,
        defaultDocumentReference: FirestoreDocument
    ) {
        moveAndUpdateValueInDocuments(
            destinationFirestoreDocument,
            arrayName,
            value,
            defaultDocumentReference,
            arrayName,
            value
        )
    }

    /**
     * Moves a value from document to another document with same array name
     *
     * @param destinationFirestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param arrayName is the reference to String, which holds name of the array, where the value is currently placed
     * @param value is the value, which will be placed in the destinationArray
     * @param defaultDocumentReference is object, which defines document in collection, where is a defaultArray, from where a defaultValue should be removed
     */
    fun moveValueToAnotherDocument(
        destinationFirestoreDocument: FirestoreDocument,
        arrayName: Int,
        value: Any,
        defaultDocumentReference: FirestoreDocument
    ) {
        moveAndUpdateValueInDocuments(
            destinationFirestoreDocument,
            arrayName,
            value,
            defaultDocumentReference,
            this.context.getString(arrayName),
            value
        )
    }

    /**
     * Updates a value from document to another document with same array name
     *
     * @param firestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param arrayName is name of the array, where the value is currently placed
     * @param value is the value, which will be placed in the array
     * @param defaultValue is the value, which will be remove in the array
     */
    fun updateValueInArray(
        firestoreDocument: FirestoreDocument,
        arrayName: String,
        value: Any,
        defaultValue: Any
    ) {
        moveAndUpdateValueInDocuments(
            firestoreDocument,
            arrayName,
            value,
            firestoreDocument,
            arrayName,
            defaultValue
        )
    }

    /**
     * Updates a value from document to another document with same array name
     *
     * @param firestoreDocument is object, which defines document in collection, where is a destinationArray, where a value should be placed
     * @param arrayName is the reference to String, which holds name of the array, where the value is currently placed
     * @param value is the value, which will be placed in the array
     * @param defaultValue is the value, which will be remove in the array
     */
    fun updateValueInArray(
        firestoreDocument: FirestoreDocument,
        arrayName: Int,
        value: Any,
        defaultValue: Any
    ) {
        moveAndUpdateValueInDocuments(
            firestoreDocument,
            arrayName,
            value,
            firestoreDocument,
            arrayName,
            defaultValue
        )
    }

    /**
     * Add increase value operation
     *
     * @param firestoreDocument is document, where the parameter is stored
     * @param paramName is name of variable, where the value is stored
     * @param howMuch is the value, how much the current value will be increased
     */
    fun increaseValue(firestoreDocument: FirestoreDocument, paramName: String, howMuch: Int) {
        getBatch().update(
            firestoreDocument.getDocumentReference(),
            paramName,
            FieldValue.increment(howMuch.toLong())
        )
    }

    /**
     * Add increase value operation
     *
     * @param firestoreDocument is document, where the parameter is stored
     * @param paramName is int reference to String, which holds the name of variable, where the value is stored
     * @param howMuch is the value, how much the current value will be increased
     */
    fun increaseValue(firestoreDocument: FirestoreDocument, paramName: Int, howMuch: Int) {
        increaseValue(firestoreDocument, this.context.getString(paramName), howMuch)
    }

    /**
     * Add increase value by one operation
     *
     * @param firestoreDocument is document, where the parameter is stored
     * @param paramName is the name of variable, where the value is stored
     */
    fun increaseValue(firestoreDocument: FirestoreDocument, paramName: String) {
        increaseValue(firestoreDocument, paramName, 1)
    }

    /**
     * Add increase value by one operation
     *
     * @param firestoreDocument is document, where the parameter is stored
     * @param paramName is int reference to String, which holds name of variable, where the value is stored
     */
    fun increaseValue(firestoreDocument: FirestoreDocument, paramName: Int) {
        increaseValue(firestoreDocument, this.context.getString(paramName), 1)
    }

    /**
     * Add decrease value operation
     *
     * @param firestoreDocument is document, where the parameter is stored
     * @param paramName is name of variable, where the value is stored
     * @param howMuch is the value, how much the current value will be increased
     */
    fun decreaseValue(firestoreDocument: FirestoreDocument, paramName: String, howMuch: Int) {
        getBatch().update(
            firestoreDocument.getDocumentReference(),
            paramName,
            FieldValue.increment(-howMuch.toLong())
        )
    }

    /**
     * Add decrease value operation
     *
     * @param firestoreDocument is document, where the parameter is stored
     * @param paramName is reference to String, which holds the name of variable, where the value is stored
     * @param howMuch is the value, how much the current value will be increased
     */
    fun decreaseValue(firestoreDocument: FirestoreDocument, paramName: Int, howMuch: Int) {
        decreaseValue(firestoreDocument, this.context.getString(paramName), howMuch)
    }

    /**
     * Add decrease value by one operation
     *
     * @param firestoreDocument is document, where the parameter is stored
     * @param paramName is name of variable, where the value is stored
     */
    fun decreaseValue(firestoreDocument: FirestoreDocument, paramName: String) {
        decreaseValue(firestoreDocument, paramName, 1)
    }

    /**
     * Add decrease value by one operation
     *
     * @param firestoreDocument is document, where the parameter is stored
     * @param paramName is reference to String, which holds the name of variable, where the value is stored
     */
    fun decreaseValue(firestoreDocument: FirestoreDocument, paramName: Int) {
        decreaseValue(firestoreDocument, this.context.getString(paramName))
    }

}