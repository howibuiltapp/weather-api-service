package com.ckai.weather.service.utils

import reactor.core.publisher.Flux
import java.nio.file.Files
import java.nio.file.Path

class RxFileReader {
    companion object {
        fun fromPath(path: Path): Flux<String> {
            return Flux.defer {
                Flux.using({ Files.lines(path) },
                    { stream -> Flux.fromStream(stream) },
                    { stream -> stream.close() })
            }
        }
    }
}