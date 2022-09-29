package com.odenzo.yang.schema

case class Module(name: String, container: Option[Any] = None)

case class Import(from: String, prefix: String)

case class Label(s: String)

case class KeyValueMeta(tipe: String, mandatory: Boolean, defaultVal: Option[String])

case class TypeDefs(name: String, tipe: String, description: Option[String])

case class Namespace(ns: String, prefix: Option[String])

/** List must have a name and I guess an optional (0/1) key.
  */
case class YList(name: String, key: Option[String], uniques: YUnique, values: YKeyValues)

/** List of keys that must be unique across objects, N/A for case class generation? */
case class YUnique(uvals: List[String])

case class YKeyValues(kv: Map[String, KeyValueMeta])
