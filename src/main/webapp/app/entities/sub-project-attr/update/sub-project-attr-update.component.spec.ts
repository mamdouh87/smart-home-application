import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SubProjectAttrService } from '../service/sub-project-attr.service';
import { ISubProjectAttr, SubProjectAttr } from '../sub-project-attr.model';
import { ISubProject } from 'app/entities/sub-project/sub-project.model';
import { SubProjectService } from 'app/entities/sub-project/service/sub-project.service';

import { SubProjectAttrUpdateComponent } from './sub-project-attr-update.component';

describe('SubProjectAttr Management Update Component', () => {
  let comp: SubProjectAttrUpdateComponent;
  let fixture: ComponentFixture<SubProjectAttrUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let subProjectAttrService: SubProjectAttrService;
  let subProjectService: SubProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SubProjectAttrUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SubProjectAttrUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SubProjectAttrUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    subProjectAttrService = TestBed.inject(SubProjectAttrService);
    subProjectService = TestBed.inject(SubProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SubProject query and add missing value', () => {
      const subProjectAttr: ISubProjectAttr = { id: 456 };
      const subProject: ISubProject = { id: 74229 };
      subProjectAttr.subProject = subProject;

      const subProjectCollection: ISubProject[] = [{ id: 73895 }];
      jest.spyOn(subProjectService, 'query').mockReturnValue(of(new HttpResponse({ body: subProjectCollection })));
      const additionalSubProjects = [subProject];
      const expectedCollection: ISubProject[] = [...additionalSubProjects, ...subProjectCollection];
      jest.spyOn(subProjectService, 'addSubProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ subProjectAttr });
      comp.ngOnInit();

      expect(subProjectService.query).toHaveBeenCalled();
      expect(subProjectService.addSubProjectToCollectionIfMissing).toHaveBeenCalledWith(subProjectCollection, ...additionalSubProjects);
      expect(comp.subProjectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const subProjectAttr: ISubProjectAttr = { id: 456 };
      const subProject: ISubProject = { id: 77192 };
      subProjectAttr.subProject = subProject;

      activatedRoute.data = of({ subProjectAttr });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(subProjectAttr));
      expect(comp.subProjectsSharedCollection).toContain(subProject);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubProjectAttr>>();
      const subProjectAttr = { id: 123 };
      jest.spyOn(subProjectAttrService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subProjectAttr });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subProjectAttr }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(subProjectAttrService.update).toHaveBeenCalledWith(subProjectAttr);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubProjectAttr>>();
      const subProjectAttr = new SubProjectAttr();
      jest.spyOn(subProjectAttrService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subProjectAttr });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: subProjectAttr }));
      saveSubject.complete();

      // THEN
      expect(subProjectAttrService.create).toHaveBeenCalledWith(subProjectAttr);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SubProjectAttr>>();
      const subProjectAttr = { id: 123 };
      jest.spyOn(subProjectAttrService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ subProjectAttr });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(subProjectAttrService.update).toHaveBeenCalledWith(subProjectAttr);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSubProjectById', () => {
      it('Should return tracked SubProject primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSubProjectById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
