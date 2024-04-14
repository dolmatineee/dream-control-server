package com.example.data.model.repository

import com.example.data.model.DreamModel
import com.example.data.model.tables.DreamTable
import com.example.domain.repository.DreamRepository
import com.example.plugins.DatabasesFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DreamRepositoryImpl: DreamRepository {

    override suspend fun addDream(dream: DreamModel) {
        dbQuery {
            DreamTable.insert { table ->
                table[owner] = dream.owner
                table[dreamTitle] = dream.dreamTitle
                table[dreamDescription] = dream.dreamDescription
                table[dreamDate] = dream.dreamDate
                table[isVerified] = dream.isVerified
            }
        }
    }

    override suspend fun getAllDreams(): List<DreamModel> {
        return dbQuery {
            DreamTable.selectAll()
                .mapNotNull { rowToDream(it) }
        }
    }

    override suspend fun updateDream(dream: DreamModel, ownerId: Int) {
        dbQuery {
            DreamTable.update(
                where = {
                    DreamTable.owner.eq(ownerId) and DreamTable.id.eq(dream.id)
                }
            ) { table ->
                table[owner] = dream.owner
                table[dreamTitle] = dream.dreamTitle
                table[dreamDescription] = dream.dreamDescription
                table[dreamDate] = dream.dreamDate
                table[isVerified] = dream.isVerified
            }
        }
    }

    override suspend fun deleteDream(dreamId: Int, ownerId: Int) {
        dbQuery {
            DreamTable.deleteWhere { DreamTable.id.eq(dreamId) and DreamTable.owner.eq(ownerId) }
        }
    }


    private fun rowToDream(row: ResultRow?): DreamModel? {
        if (row == null) {
            return null
        }

        return DreamModel(
            id = row[DreamTable.id],
            owner = row[DreamTable.owner],
            dreamTitle = row[DreamTable.dreamTitle],
            dreamDescription = row[DreamTable.dreamDescription],
            dreamDate = row[DreamTable.dreamDate],
            isVerified = row[DreamTable.isVerified],
        )
    }


}