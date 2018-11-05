[com.hedgehog.gdzietabiedra.data.repository.shops](../index.md) / [ShopsRepository](./index.md)

# ShopsRepository

`class ShopsRepository`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ShopsRepository(realmConfiguration: RealmConfiguration)` |

### Functions

| Name | Summary |
|---|---|
| [fetchAll](fetch-all.md) | `fun fetchAll(): `[`Flowable`](http://reactivex.io/RxJava/javadoc/io/reactivex/Flowable.html)`<Shop>` |
| [fetchByAddress](fetch-by-address.md) | `fun fetchByAddress(address: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, count: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Flowable`](http://reactivex.io/RxJava/javadoc/io/reactivex/Flowable.html)`<Shop>` |
| [fetchById](fetch-by-id.md) | `fun fetchById(id: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Single`](http://reactivex.io/RxJava/javadoc/io/reactivex/Single.html)`<Shop>` |
| [fetchByLocationAndRange](fetch-by-location-and-range.md) | `fun fetchByLocationAndRange(location: Position, range: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, count: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Flowable`](http://reactivex.io/RxJava/javadoc/io/reactivex/Flowable.html)`<Shop>` |
| [save](save.md) | `fun save(apiModel: `[`ShopsItem`](../../com.hedgehog.gdzietabiedra.api.response.shop/-shops-item/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [saveAll](save-all.md) | `fun saveAll(apiModels: `[`Collection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)`<`[`ShopsItem`](../../com.hedgehog.gdzietabiedra.api.response.shop/-shops-item/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |