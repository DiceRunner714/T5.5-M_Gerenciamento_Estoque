from sqlalchemy.orm import Session
from config.database import SessionLocal
from model.empresa_orm import EmpresaORM
from model.filial_orm import FilialORM
from model.produto_quimico_orm import ProdutoQuimicoORM


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


def criar_empresa(nome: str) -> EmpresaORM:
    db: Session = next(get_db())
    nova = EmpresaORM(nome=nome)
    db.add(nova)
    db.commit()
    db.refresh(nova)
    return nova


def listar_empresas() -> list[EmpresaORM]:
    db: Session = next(get_db())
    return db.query(EmpresaORM).all()


def buscar_empresa_por_id(id: int) -> EmpresaORM | None:
    db: Session = next(get_db())
    return db.query(EmpresaORM).filter(EmpresaORM.id == id).first()


def remover_empresa(id: int) -> bool:
    db: Session = next(get_db())
    empresa = db.query(EmpresaORM).filter(EmpresaORM.id == id).first()
    if empresa:
        db.delete(empresa)
        db.commit()
        return True
    return False


def calcular_estoque_total_empresa(empresa_id: int):
    db: Session = SessionLocal()

    filiais = db.query(FilialORM).filter(FilialORM.empresa_id == empresa_id).all()
    if not filiais:
        return None

    filial_ids = [f.id for f in filiais]

    produtos = db.query(ProdutoQuimicoORM).filter(
        ProdutoQuimicoORM.filial_id.in_(filial_ids)
    ).all()

    total_estoque = sum(p.quantidade for p in produtos)

    lista_produtos = [
        {
            "id": p.id,
            "nome": p.nome,
            "quantidade": p.quantidade,
            "categoria": p.categoria,
            "filial_id": p.filial_id
        }
        for p in produtos
    ]

    return {"empresa_id": empresa_id, "estoque_total": total_estoque, "produtos": lista_produtos}
