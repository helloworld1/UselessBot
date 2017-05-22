package org.liberty.android.uselessbot.model

class QueryResponse (
        val id: String,
        val timestamp: String,
        val result: QueryResult
)

class QueryResult(
        val resolvedQuery: String,
        val speech: String,
        val action: String
)
