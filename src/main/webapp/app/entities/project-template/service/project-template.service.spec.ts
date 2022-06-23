import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjectTemplate, ProjectTemplate } from '../project-template.model';

import { ProjectTemplateService } from './project-template.service';

describe('ProjectTemplate Service', () => {
  let service: ProjectTemplateService;
  let httpMock: HttpTestingController;
  let elemDefault: IProjectTemplate;
  let expectedResult: IProjectTemplate | IProjectTemplate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProjectTemplateService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      projectTemplateCode: 'AAAAAAA',
      projectTemplateNameAr: 'AAAAAAA',
      projectTemplateNameEn: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ProjectTemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ProjectTemplate()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProjectTemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          projectTemplateCode: 'BBBBBB',
          projectTemplateNameAr: 'BBBBBB',
          projectTemplateNameEn: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProjectTemplate', () => {
      const patchObject = Object.assign(
        {
          projectTemplateCode: 'BBBBBB',
          projectTemplateNameAr: 'BBBBBB',
          projectTemplateNameEn: 'BBBBBB',
        },
        new ProjectTemplate()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProjectTemplate', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          projectTemplateCode: 'BBBBBB',
          projectTemplateNameAr: 'BBBBBB',
          projectTemplateNameEn: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ProjectTemplate', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProjectTemplateToCollectionIfMissing', () => {
      it('should add a ProjectTemplate to an empty array', () => {
        const projectTemplate: IProjectTemplate = { id: 123 };
        expectedResult = service.addProjectTemplateToCollectionIfMissing([], projectTemplate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectTemplate);
      });

      it('should not add a ProjectTemplate to an array that contains it', () => {
        const projectTemplate: IProjectTemplate = { id: 123 };
        const projectTemplateCollection: IProjectTemplate[] = [
          {
            ...projectTemplate,
          },
          { id: 456 },
        ];
        expectedResult = service.addProjectTemplateToCollectionIfMissing(projectTemplateCollection, projectTemplate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProjectTemplate to an array that doesn't contain it", () => {
        const projectTemplate: IProjectTemplate = { id: 123 };
        const projectTemplateCollection: IProjectTemplate[] = [{ id: 456 }];
        expectedResult = service.addProjectTemplateToCollectionIfMissing(projectTemplateCollection, projectTemplate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectTemplate);
      });

      it('should add only unique ProjectTemplate to an array', () => {
        const projectTemplateArray: IProjectTemplate[] = [{ id: 123 }, { id: 456 }, { id: 98255 }];
        const projectTemplateCollection: IProjectTemplate[] = [{ id: 123 }];
        expectedResult = service.addProjectTemplateToCollectionIfMissing(projectTemplateCollection, ...projectTemplateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const projectTemplate: IProjectTemplate = { id: 123 };
        const projectTemplate2: IProjectTemplate = { id: 456 };
        expectedResult = service.addProjectTemplateToCollectionIfMissing([], projectTemplate, projectTemplate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectTemplate);
        expect(expectedResult).toContain(projectTemplate2);
      });

      it('should accept null and undefined values', () => {
        const projectTemplate: IProjectTemplate = { id: 123 };
        expectedResult = service.addProjectTemplateToCollectionIfMissing([], null, projectTemplate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectTemplate);
      });

      it('should return initial array if no ProjectTemplate is added', () => {
        const projectTemplateCollection: IProjectTemplate[] = [{ id: 123 }];
        expectedResult = service.addProjectTemplateToCollectionIfMissing(projectTemplateCollection, undefined, null);
        expect(expectedResult).toEqual(projectTemplateCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
