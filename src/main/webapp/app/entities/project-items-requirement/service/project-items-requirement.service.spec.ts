import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjectItemsRequirement, ProjectItemsRequirement } from '../project-items-requirement.model';

import { ProjectItemsRequirementService } from './project-items-requirement.service';

describe('ProjectItemsRequirement Service', () => {
  let service: ProjectItemsRequirementService;
  let httpMock: HttpTestingController;
  let elemDefault: IProjectItemsRequirement;
  let expectedResult: IProjectItemsRequirement | IProjectItemsRequirement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProjectItemsRequirementService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      qtyNo: 0,
      notes: 'AAAAAAA',
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

    it('should create a ProjectItemsRequirement', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ProjectItemsRequirement()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProjectItemsRequirement', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          qtyNo: 1,
          notes: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProjectItemsRequirement', () => {
      const patchObject = Object.assign(
        {
          notes: 'BBBBBB',
        },
        new ProjectItemsRequirement()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProjectItemsRequirement', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          qtyNo: 1,
          notes: 'BBBBBB',
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

    it('should delete a ProjectItemsRequirement', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProjectItemsRequirementToCollectionIfMissing', () => {
      it('should add a ProjectItemsRequirement to an empty array', () => {
        const projectItemsRequirement: IProjectItemsRequirement = { id: 123 };
        expectedResult = service.addProjectItemsRequirementToCollectionIfMissing([], projectItemsRequirement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectItemsRequirement);
      });

      it('should not add a ProjectItemsRequirement to an array that contains it', () => {
        const projectItemsRequirement: IProjectItemsRequirement = { id: 123 };
        const projectItemsRequirementCollection: IProjectItemsRequirement[] = [
          {
            ...projectItemsRequirement,
          },
          { id: 456 },
        ];
        expectedResult = service.addProjectItemsRequirementToCollectionIfMissing(
          projectItemsRequirementCollection,
          projectItemsRequirement
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProjectItemsRequirement to an array that doesn't contain it", () => {
        const projectItemsRequirement: IProjectItemsRequirement = { id: 123 };
        const projectItemsRequirementCollection: IProjectItemsRequirement[] = [{ id: 456 }];
        expectedResult = service.addProjectItemsRequirementToCollectionIfMissing(
          projectItemsRequirementCollection,
          projectItemsRequirement
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectItemsRequirement);
      });

      it('should add only unique ProjectItemsRequirement to an array', () => {
        const projectItemsRequirementArray: IProjectItemsRequirement[] = [{ id: 123 }, { id: 456 }, { id: 9273 }];
        const projectItemsRequirementCollection: IProjectItemsRequirement[] = [{ id: 123 }];
        expectedResult = service.addProjectItemsRequirementToCollectionIfMissing(
          projectItemsRequirementCollection,
          ...projectItemsRequirementArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const projectItemsRequirement: IProjectItemsRequirement = { id: 123 };
        const projectItemsRequirement2: IProjectItemsRequirement = { id: 456 };
        expectedResult = service.addProjectItemsRequirementToCollectionIfMissing([], projectItemsRequirement, projectItemsRequirement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projectItemsRequirement);
        expect(expectedResult).toContain(projectItemsRequirement2);
      });

      it('should accept null and undefined values', () => {
        const projectItemsRequirement: IProjectItemsRequirement = { id: 123 };
        expectedResult = service.addProjectItemsRequirementToCollectionIfMissing([], null, projectItemsRequirement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projectItemsRequirement);
      });

      it('should return initial array if no ProjectItemsRequirement is added', () => {
        const projectItemsRequirementCollection: IProjectItemsRequirement[] = [{ id: 123 }];
        expectedResult = service.addProjectItemsRequirementToCollectionIfMissing(projectItemsRequirementCollection, undefined, null);
        expect(expectedResult).toEqual(projectItemsRequirementCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
