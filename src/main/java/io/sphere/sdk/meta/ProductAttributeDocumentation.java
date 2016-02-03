package io.sphere.sdk.meta;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.ProductVariantDraftBuilder;
import io.sphere.sdk.products.attributes.AttributeAccess;

/**
 <h3 id="intro">Introduction</h3>
 <h3 id="product-type-creation">ProductType Creation</h3>
 <p>A {@link io.sphere.sdk.producttypes.ProductType} is like a schema that defines how the product attributes are structured.</p>
 <p>{@link io.sphere.sdk.producttypes.ProductType}s contain a list of {@link io.sphere.sdk.products.attributes.AttributeDefinition}s which corresponds to the name and type of each attribute, along with some additional information".
 Each name/type pair must be unique across a SPHERE.IO project, so if you create an attribute "foo" of type String, you cannot create
 another {@link io.sphere.sdk.producttypes.ProductType} where "foo" has another type (e.g. {@link LocalizedString}). If you do it anyway you get an error message like:</p>

<pre>"The attribute with name 'foo' has a different type on product type 'exampleproducttype'."</pre>

 <p>In this scenario we provide two {@link io.sphere.sdk.producttypes.ProductType}s <strong>book</strong> and <strong>tshirt</strong>.</p>


 <p>The book product type contains the following attributes:</p>
 <ul>
    <li>{@code isbn} as {@link String}, International Standard Book Number</li>
 </ul>

 <p>The tshirt product type contains the following attributes:</p>

 <ul>
    <li>{@code color} as {@link io.sphere.sdk.models.LocalizedEnumValue} with the colors green and red and their translations in German and English</li>
    <li>{@code size} as {@link io.sphere.sdk.models.EnumValue} with S, M and X</li>
    <li>{@code laundrySymbols} as set of {@link io.sphere.sdk.models.LocalizedEnumValue} with temperature and tumble drying</li>
    <li>{@code matchingProducts} as set of product {@link io.sphere.sdk.models.Reference}s, which can point to products that are similar to the current product</li>
    <li>{@code rrp} as {@link javax.money.MonetaryAmount} containing the recommended retail price</li>
    <li>{@code availableSince} as {@link java.time.LocalDateTime} which contains the date since when the product is available for the customer in the shop</li>
 </ul>



 <p>All available attribute types you can find here: {@link io.sphere.sdk.products.attributes.AttributeType} in "All Known Implementing Classes".</p>

<p>The code for the creation of the book {@link io.sphere.sdk.producttypes.ProductType}:</p>
 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#createBookProductType()}

<p>The code for the creation of the tshirt {@link io.sphere.sdk.producttypes.ProductType}:</p>
 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#createProductType()}



 <p>{@link io.sphere.sdk.producttypes.ProductType}s have a key (String)
 which can be used as key to logically identify {@link io.sphere.sdk.producttypes.ProductType}s. The key has an unique constraint.

 <h3 id="product-creation">Product Creation</h3>

 <p>To create a product you need to reference the product type. Since the {@link io.sphere.sdk.producttypes.ProductType}
 ID of the development system will not be the ID of the production system it is necessary to find the product type by name:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#fetchProductTypeByName()}

 The simplest way of adding attributes to a {@link io.sphere.sdk.products.ProductVariant} is to use
 {@link ProductVariantDraftBuilder#plusAttribute(java.lang.String, java.lang.Object)} which enables you to directly
 put the value of the attribute to the draft. But it cannot check if you put the right objects and types in it.

 <p>A book example:</p>
 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#createBookProduct()}

 <p>A tshirt example:</p>
 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#createProduct()}

 A wrong value for a field or an invalid type will cause an {@link io.sphere.sdk.client.ErrorResponseException}
 with an error code of {@value io.sphere.sdk.models.errors.InvalidField#CODE}.

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#invalidTypeCausesException()}

 As alternative you could declare your attributes at the same place and use these to read and write
 attribute values:

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#productCreationWithGetterSetter()}


 <h3 id="attribute-access-without-product-type">Reading Attributes</h3>

 <p>To get a value out of an attribute you need an instance of {@link AttributeAccess}
 which keeps the type info to deserialize the attribute.</p>

 <p>You can reuse the {@link io.sphere.sdk.products.attributes.NamedAttributeAccess} declaration if you want to:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#readAttributeWithoutProductTypeWithNamedAccess()}

 <p>Or you can access it on the fly:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#readAttributeWithoutProductTypeWithName()}

 <p>Or you can access it as {@link com.fasterxml.jackson.databind.JsonNode}, for example if you don't know the type or the SDK does not support it yet:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#readAttributeWithoutProductTypeWithJson()}


 <p>If the attribute is not present in the {@link io.sphere.sdk.products.AttributeContainer} then the {@link java.util.Optional} will be empty:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#notPresentAttributeRead()}

 <p>If you provide a wrong type, the code will throw a {@link io.sphere.sdk.json.JsonException}:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#readAttributeWithoutProductTypeWithNamedAccessWithWrongType()}



 <h3 id="attribute-table-creation">Creating a table of attributes</h3>

<p>With the help of the product type, you can display a table with attributes. Such as:</p>

 <pre><code>
 color                    | green
 ----------------------------------------------------------------
 size                     | S
 ----------------------------------------------------------------
 matching products        | referenceable product
 ----------------------------------------------------------------
 washing labels           | tumble drying, Wash at or below 30°C
 ----------------------------------------------------------------
 recommended retail price | EUR300.00
 ----------------------------------------------------------------
 available since          | 2015-02-02
 ----------------------------------------------------------------</code></pre>

 <p>In this example the left column is the label of
 the attribute from the product type and the right column is the formatted value from the product:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#showProductAttributeTable()}

 <!-- it is easy to mix uo product and product type with autocomplete and using the non set variant where set is expected -->


 <h3 id="attribute-update">Update attribute values of a product</h3>

<p>Setting attribute values is like a a product creation:</p>


<p>Example for books:</p>
 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#updateAttributesBooks()}

 <p>Example for tshirts:</p>
 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#updateAttributes()}

 A wrong value for a field or an invalid type (like flat value instead of set) will cause an {@link io.sphere.sdk.client.ErrorResponseException}
 with an error code of {@value io.sphere.sdk.models.errors.InvalidField#CODE}.

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#updateWithWrongType()}

 <h3 id="attribute-stubs-for-tests">Create attribute stubs for unit tests</h3>

 <p>For unit tests you can create an {@link io.sphere.sdk.products.attributes.Attribute} with a
 static factory method such as {@link io.sphere.sdk.products.attributes.Attribute#of(String, AttributeAccess, Object)}:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#attributesForUnitTests()}

 <h3 id="attributes-in-order-import">Create attributes for importing orders</h3>

 <p>Importing attribute values for orders works different from updating products.
 In orders you provide the full value for enum-like types instead of just the key as done for all other types.
 This makes it possible to create a new enum value on the fly. The other attributes behave as expected.</p>

 <p>Example:</p>

 {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoTest#orderImportExample()}



<h3 id="attribute-class-semantics">Semantics of the Attribute classes</h3>

 <table>
    <caption>attribute properties</caption>
    <tr>
        <th></th><th>{@link io.sphere.sdk.products.attributes.Attribute}</th><th>{@link io.sphere.sdk.products.attributes.AttributeDraft}</th><th>{@link io.sphere.sdk.products.attributes.AttributeImportDraft}</th>
    </tr>
 <tr> <td>purpose</td>    <td>read access</td>    <td>write product, create product</td>    <td>order import</td></tr>
 <tr> <td>reference expansion</td>    <td>keeps expanded references</td>    <td>no expansion</td>    <td>no expansion</td></tr>
 <tr> <td>enum shape</td>    <td>full enum</td>    <td>enum key (String)</td>    <td>full enum</td></tr>
 <tr> <td>value constraints</td>    <td>value in product type</td>    <td>value in product type</td>    <td>free, create on the fly</td></tr>
 </table>

 <h3 id="nested-attributes">Nested attributes (experimental)</h3>

 <p>This feature is experimental.</p>
 <p>An example:</p>

 {@include.example io.sphere.sdk.producttypes.NestedAttributeIntegrationTest}

 <p>A general explanation can be found in the  <a href="http://dev.sphere.io/tutorials-nested-types.html">HTTP API tutorial</a>.</p>
 */
public final class ProductAttributeDocumentation {
    private ProductAttributeDocumentation() {
    }
}
