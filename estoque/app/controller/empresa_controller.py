from sqlalchemy.orm import Session
from config.database import SessionLocal
from model.empresa_orm import EmpresaORM


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
