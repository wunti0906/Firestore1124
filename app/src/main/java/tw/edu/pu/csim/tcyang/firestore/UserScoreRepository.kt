package tw.edu.pu.csim.tcyang.firestore

import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import kotlin.text.set

class UserScoreRepository {
    val db = Firebase.firestore

    suspend fun addUser(userScore: UserScoreModel): String {
        return try {
            val documentReference =
                db.collection("UserScore")
                    .add(userScore)
                    .await()
            "新增資料成功！Document ID:\n ${documentReference.id}"
        } catch (e: Exception) {
            // await() 失敗時會拋出例外，在這裡捕捉並處理
            "新增資料失敗：${e.message}"
        }
    }

    suspend fun updateUser(userScore: UserScoreModel): String {
        return try {
            db.collection("UserScore")
                .document(userScore.user)
                .set(userScore)
                .await()
            "新增/異動資料成功！Document ID:\n ${userScore.user}"
        } catch (e: Exception) {
            // await() 失敗時會拋出例外，在這裡捕捉並處理
            "新增/異動資料失敗：${e.message}"
        }
    }

    suspend fun deleteUser(userScore: UserScoreModel): String {
        return try {
            // 1. 取得文件參考
            val documentRef = db.collection("UserScore").document(userScore.user)

            // 2. 執行讀取操作，確認文件是否存在
            val documentSnapshot = documentRef.get().await()

            if (documentSnapshot.exists()) {
                // 3. 如果文件存在，才執行刪除
                documentRef.delete().await()
                "刪除資料成功！Document ID: ${userScore.user}"
            } else {
                // 4. 如果文件不存在，回傳對應的訊息
                "刪除失敗：Document ID ${userScore.user} 不存在。"
            }

        } catch (e: Exception) {
            // await() 失敗時會拋出例外，在這裡捕捉並處理
            "刪除資料失敗：${e.message}"
        }
    }


    suspend fun getUserScoreByName(userScore: UserScoreModel): String {
        return try {
            var userCondition = "彣媞"
            val querySnapshot = db.collection("UserScore")
                .whereEqualTo("user", userCondition) // 篩選條件
                .get().await()
            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents.first() // 取得第一個符合條件的文件
                val userScore = document.toObject<UserScoreModel>()
                "查詢成功！${userScore?.user} 的分數是 ${userScore?.score}"
            } else {
                "查詢失敗：找不到使用者 $userCondition 的資料。"
            }
        } catch (e: Exception) {
            // await() 失敗時會拋出例外，在這裡捕捉並處理
            "查詢資料失敗：${e.message}"
        }
    }

    suspend fun orderByScore(): String {
        return try {
            var message = ""
            val querySnapshot = db.collection("UserScore")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(3).get().await()

            // 使用 forEach 迴圈遍歷所有文件
            querySnapshot.documents.forEach { document ->
                // 將文件轉換為 UserScoreModel
                val userScore = document.toObject<UserScoreModel>()

                // 檢查是否成功轉換，並將分數加入字串
                userScore?.let {
                    message += "使用者 ${it.user} 的分數為 ${it.score} \n"
                }
            }
            if (message.isEmpty()) {
                message = "抱歉，資料庫目前無相關資料"
            } else {
                message = "查詢成功！分數由大到小排序為：\n" + message
            }
            message

        } catch (e: Exception) {
            // await() 失敗時會拋出例外，在這裡捕捉並處理
            "查詢資料失敗：${e.message}"
        }
    }

}