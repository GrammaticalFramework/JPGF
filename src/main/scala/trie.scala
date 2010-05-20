package pgf.util

import scala.collection.mutable.HashMap

class Trie[K,V](var value:Option[V]) {
  val child = new HashMap[K,Trie[K,V]]

  def this(value:V) = this(Some(value))
  def this() = this(None)

  def add(key:Seq[K], value:V):Unit = this.add(key.toList, value)

  def add(key:List[K], value:V):Unit = key match {
    case Nil => this.value = Some(value)
    case x::l => this.child.get(x) match {
      case None => {
        val newN = new Trie[K,V]
        newN.add(l,value)
        this.child.update(x, newN)
      }
      case Some(n) => n.add(l,value)
    }
  }

  def lookup(key:Seq[K]):Option[V] = this.lookup(key.toList)

  def lookup(key:List[K]):Option[V] = getSubTrie(key) match {
    case None => None
    case Some(t) => t.value
  }

  def lookup(key:K):Option[V] = this.lookup(key::Nil)

  def getSubTrie(key:List[K]):Option[Trie[K,V]] = key match {
    case Nil => Some(this)
    case x::l => this.child.get(x) match {
      case None => None
      case Some(n) => n.getSubTrie(l)
    }
  }

  def getSubTrie(key:K):Option[Trie[K,V]] = this.getSubTrie(key::Nil)

  override def toString() = this.toStringWithPrefix("")

  def toStringWithPrefix(prefix:String):String = {
    prefix + "<" + this.value + ">" +
    this.child.keys.map(k =>
      prefix + k.toString + ":\n" +
      this.child(k).toStringWithPrefix(prefix + "  ")
    ).foldLeft("")((a:String,b:String) => a + "\n" + b)
  }
}
