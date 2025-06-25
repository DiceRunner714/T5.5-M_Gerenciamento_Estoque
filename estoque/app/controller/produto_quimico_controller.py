from sqlalchemy.orm import Session
from config.database import SessionLocal
from model.produto_quimico_orm import ProdutoQuimicoORM

def criar_produto_quimico(dados) -> ProdutoQuimicoORM:
    db: Session = SessionLocal()
    restrito = dados.toxicidade >= 7
    novo = ProdutoQuimicoORM(
        nome=dados.nome,
        categoria=dados.categoria,
        valor=dados.valor,
        quantidade=dados.quantidade,
        toxicidade=dados.toxicidade,
        restrito=restrito,
        filial_id=dados.filial_id
    )
    db.add(novo)
    db.commit()
    db.refresh(novo)
    return novo

def listar_produtos() -> list[ProdutoQuimicoORM]:
    db: Session = SessionLocal()
    return db.query(ProdutoQuimicoORM).all()

def buscar_produto_por_id(id: int) -> ProdutoQuimicoORM | None:
    db: Session = SessionLocal()
    return db.query(ProdutoQuimicoORM).filter(ProdutoQuimicoORM.id == id).first()

def remover_produto(id: int) -> bool:
    db: Session = SessionLocal()
    produto = db.query(ProdutoQuimicoORM).filter(ProdutoQuimicoORM.id == id).first()
    if produto:
        db.delete(produto)
        db.commit()
        return True
    return False
