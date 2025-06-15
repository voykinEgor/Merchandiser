package com.example.merchandiser.domain.nearestNeighbor

import com.yandex.mapkit.geometry.Point
import kotlin.math.*

class HaversineDistance {

    companion object{

        fun haversineDistance(pointStart: Point, pointFinish: Point): Double {
            val R = 6371.0 // Радиус Земли в километрах
            val lat1 = Math.toRadians(pointStart.latitude)
            val lon1 = Math.toRadians(pointStart.longitude)
            val lat2 = Math.toRadians(pointFinish.latitude)
            val lon2 = Math.toRadians(pointFinish.longitude)

            val dLat = lat2 - lat1
            val dLon = lon2 - lon1

            val a = sin(dLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            return R * c
        }

    }

}