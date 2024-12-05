package io.captaingaga.airtickets.effective.mobile.network

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.util.concurrent.TimeUnit

class HttpClient {

    operator fun invoke(): OkHttpClient {
        val timeOut = 20L
        return OkHttpClient().newBuilder()
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .addInterceptor(MockInterceptor())
            .build()
    }

    private class MockInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val responseString: String = when (chain.request().url().encodedPath()) {
                "/offers" -> RAW_OFFERS_JSON

                "/tickets" -> RAW_TICKETS_JSON

                "/tickets_offers" -> RAW_TICKETS_OFFERS_JSON

                else -> "{}"
            }
            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_1_1)
                .message("ok")
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        responseString
                    )
                )
                .build()
        }
    }

    private companion object {
        const val RAW_OFFERS_JSON: String = """
                        {
                          "offers": [
                            {
                              "id": 1,
                              "title": "Die Antwoord",
                              "town": "Будапешт",
                              "price": {
                                "value": 5000
                              }
                            },
                            {
                              "id": 2,
                              "title": "Socrat&Lera",
                              "town": "Санкт-Петербург",
                              "price": {
                                "value": 1999
                              }
                            },
                            {
                              "id": 3,
                              "title": "Лампабикт",
                              "town": "Москва",
                              "price": {
                                "value": 2390
                              }
                            }
                          ]
                        }
                    """
        const val RAW_TICKETS_JSON: String = """
                        {
                          "tickets": [
                            {
                              "id": 100,
                              "badge": "Самый удобный",
                              "price": {
                                "value": 1999
                              },
                              "provider_name": "На сайте Купибилет",
                              "company": "Якутия",
                              "departure": {
                                "town": "Москва",
                                "date": "2024-02-23T03:15:00",
                                "airport": "VKO"
                              },
                              "arrival": {
                                "town": "Сочи",
                                "date": "2024-02-23T07:10:00",
                                "airport": "AER"
                              },
                              "has_transfer": false,
                              "has_visa_transfer": false,
                              "luggage": {
                                "has_luggage": false,
                                "price": {
                                  "value": 1082
                                }
                              },
                              "hand_luggage": {
                                "has_hand_luggage": true,
                                "size": "55x20x40"
                              },
                              "is_returnable": false,
                              "is_exchangeable": true
                            },
                            {
                              "id": 101,
                              "price": {
                                "value": 9999
                              },
                              "provider_name": "На сайте Победа",
                              "company": "Победа",
                              "departure": {
                                "town": "Москва",
                                "date": "2024-02-23T04:00:00",
                                "airport": "VKO"
                              },
                              "arrival": {
                                "town": "Сочи",
                                "date": "2024-02-23T08:30:00",
                                "airport": "AER"
                              },
                              "has_transfer": false,
                              "has_visa_transfer": false,
                              "luggage": {
                                "has_luggage": false,
                                "price": {
                                  "value": 1602
                                }
                              },
                              "hand_luggage": {
                                "has_hand_luggage": true,
                                "size": "36x30x27"
                              },
                              "is_returnable": false,
                              "is_exchangeable": true
                            },
                            {
                              "id": 102,
                              "price": {
                                "value": 8500
                              },
                              "provider_name": "На сайте Turkish Airlines",
                              "company": "Турецкие авиалинии",
                              "departure": {
                                "town": "Москва",
                                "date": "2024-02-23T15:00:00",
                                "airport": "VKO"
                              },
                              "arrival": {
                                "town": "Сочи",
                                "date": "2024-02-23T18:40:00",
                                "airport": "AER"
                              },
                              "has_transfer": false,
                              "has_visa_transfer": false,
                              "luggage": {
                                "has_luggage": true
                              },
                              "hand_luggage": {
                                "has_hand_luggage": true,
                                "size": "55x20x40"
                              },
                              "is_returnable": false,
                              "is_exchangeable": false
                            },
                            {
                              "id": 103,
                              "badge": "Рекомендуемый",
                              "price": {
                                "value": 8086
                              },
                              "provider_name": "На сайте Уральские авиалинии",
                              "company": "Уральские авиалинии",
                              "departure": {
                                "town": "Москва",
                                "date": "2024-02-23T11:30:00",
                                "airport": "VKO"
                              },
                              "arrival": {
                                "town": "Сочи",
                                "date": "2024-02-23T15:00:00",
                                "airport": "AER"
                              },
                              "has_transfer": false,
                              "has_visa_transfer": false,
                              "luggage": {
                                "has_luggage": false,
                                "price": {
                                  "value": 1570
                                }
                              },
                              "hand_luggage": {
                                "has_hand_luggage": true,
                                "size": "55x20x40"
                              },
                              "is_returnable": true,
                              "is_exchangeable": true
                            },
                            {
                              "id": 104,
                              "price": {
                                "value": 11560
                              },
                              "provider_name": "На сайте Aeroflot",
                              "company": "Аэрофлот",
                              "departure": {
                                "town": "Москва",
                                "date": "2024-02-23T11:50:00",
                                "airport": "VKO"
                              },
                              "arrival": {
                                "town": "Сочи",
                                "date": "2024-02-23T15:20:00",
                                "airport": "AER"
                              },
                              "has_transfer": true,
                              "has_visa_transfer": false,
                              "luggage": {
                                "has_luggage": false,
                                "price": {
                                  "value": 999
                                }
                              },
                              "hand_luggage": {
                                "has_hand_luggage": true,
                                "size": "55x20x40"
                              },
                              "is_returnable": false,
                              "is_exchangeable": true
                            },
                            {
                              "id": 105,
                              "price": {
                                "value": 15600
                              },
                              "provider_name": "На сайте Aeroflot",
                              "company": "Аэрофлот",
                              "departure": {
                                "town": "Москва",
                                "date": "2024-02-23T13:50:00",
                                "airport": "VKO"
                              },
                              "arrival": {
                                "town": "Сочи",
                                "date": "2024-02-23T17:20:00",
                                "airport": "AER"
                              },
                              "has_transfer": true,
                              "has_visa_transfer": true,
                              "luggage": {
                                "has_luggage": false,
                                "price": {
                                  "value": 1999
                                }
                              },
                              "hand_luggage": {
                                "has_hand_luggage": true,
                                "size": "55x20x40"
                              },
                              "is_returnable": true,
                              "is_exchangeable": true
                            },
                            {
                              "id": 106,
                              "price": {
                                "value": 9500
                              },
                              "provider_name": "На сайте Aeroflot",
                              "company": "Аэрофлот",
                              "departure": {
                                "town": "Москва",
                                "date": "2024-02-23T21:00:00",
                                "airport": "VKO"
                              },
                              "arrival": {
                                "town": "Сочи",
                                "date": "2024-02-24T00:20:00",
                                "airport": "AER"
                              },
                              "has_transfer": false,
                              "has_visa_transfer": false,
                              "luggage": {
                                "has_luggage": false,
                                "price": {
                                  "value": 5999
                                }
                              },
                              "hand_luggage": {
                                "has_hand_luggage": false
                              },
                              "is_returnable": false,
                              "is_exchangeable": false
                            }
                          ]
                        }
                    """
        const val RAW_TICKETS_OFFERS_JSON: String = """
                        {
                          "tickets_offers": [
                            {
                              "id": 1,
                              "title": "Уральские авиалинии",
                              "time_range": [
                                "07:00",
                                "09:10",
                                "10:00",
                                "11:30",
                                "14:15",
                                "19:10",
                                "21:00",
                                "23:30"
                              ],
                              "price": {
                                "value": 3999
                              }
                            },
                            {
                              "id": 10,
                              "title": "Победа",
                              "time_range": [
                                "09:10",
                                "21:00"
                              ],
                              "price": {
                                "value": 4999
                              }
                            },
                            {
                              "id": 30,
                              "title": "NordStar",
                              "time_range": [
                                "07:00"
                              ],
                              "price": {
                                "value": 2390
                              }
                            }
                          ]
                        }
                    """
    }
}